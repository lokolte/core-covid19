package com.core.covid19.services;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import com.core.covid19.models.entities.*;
import com.core.covid19.models.enums.Roles;
import com.core.covid19.models.responses.*;
import com.core.covid19.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.covid19.models.requests.PersonRequest;

@Service
public class PersonService {

	@Autowired
	private LocationRepo locationRepo;

	@Autowired
	private StatusRepo statusRepo;

	@Autowired
	private PersonRepo personRepo;

	@Autowired
	private AccountRepo accountRepo;
	
	@Autowired
	private FormService formService;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private MessageRepo messageRepo;

	@Autowired
	PatientDoctorRepo patientDoctorRepo;

	public Person insert(PersonRequest personRequest, String email) {
		Location location = new Location();
		location.setLatitude(personRequest.getLocation().getLatitude());
		location.setLongitude(personRequest.getLocation().getLongitude());

		Location locationStored = locationRepo.save(location);

		Status status = statusRepo.findByName(personRequest.getStatus().getName());

		Account account = accountRepo.findByEmail(email);

		Person person = new Person();
		person.setDocument(personRequest.getDocument());
		person.setName(personRequest.getName());
		person.setLastname(personRequest.getLastname());
		person.setPhone(personRequest.getPhone());
		person.setSex(personRequest.getSex());
		person.setLocation(locationStored);
		person.setStatus(status);
		person.setPersonForms(formService.getDefaultForms()); //agregar formularios a la persona

		Person personResult = personRepo.save(person);

		account.setPerson(person);
		accountRepo.save(account);

		return personResult;
	}

	public List<Person> findAll() {
		return personRepo.findAll();
	}

	public PatientsResponse getPatientsDoctor(int id) {

		Optional<Person> per = personRepo.findById(id);
		if (per == null || !per.isPresent()) return new PatientsResponse();
		Person person = per.get();
		List<Person> patiens = patientDoctorRepo.getPatients(person.getId());
		List<PersonResponse> list = new ArrayList<>();
		for (Person p : patiens) {
			list.add(new PersonResponse(p));
		}
		return new PatientsResponse(list);
	}

	public MessageResponse getMessages(int id, int idPatient) {

		Optional<Person> per = personRepo.findById(id);
		if (per == null || !per.isPresent()) return new MessageResponse();
		Person person = per.get();

		List<Message> receiverMessages = messageRepo.findByPersonReceivedId(idPatient);
		List<Message> senderMessages = messageRepo.findByPersonSenderId(idPatient);

		List<MessageItem> messages = new ArrayList<MessageItem>();

		for(Message message : receiverMessages)
			messages.add(new MessageItem(message.getId(), message.getMessageText(), message.getSendDate(), personRepo.findById(message.getPersonSenderId()).get(), false));

		for(Message message : senderMessages)
			messages.add(new MessageItem(message.getId(), message.getMessageText(), message.getSendDate(), personRepo.findById(message.getPersonSenderId()).get(), true));

		messages = messages.stream().sorted().collect(Collectors.toList());

		return new MessageResponse(messages, person);
	}

	public void sendMessage(int id, int idPatient, String message) {

		Message m = new Message();
		m.setPersonSenderId(id);
		m.setPersonReceivedId(idPatient);
		m.setMessageText(message);
		m.setSendDate(new Timestamp(new Date().getTime()));
		messageRepo.save(m);
	}
	
	public PersonsResponse getPatients(String email) {
		
		Account account = accountRepo.findByEmail(email);
		if (account.getPerson() == null) return new PersonsResponse();
		Person person = account.getPerson();
		if (person.getProvince() == null) return new PersonsResponse();
		
		List<Person> persons = personRepo.getPatients(person.getProvince().getId());
		List<PersonResponse> list = new ArrayList<PersonResponse>();
		for (Person p : persons) {
			Person doctor = patientDoctorRepo.getDoctorPatient(p.getId());
			String d = doctor == null ? "Sin asignar" : doctor.getName() + " " + doctor.getLastname();
			list.add(new PersonResponse(p, d));
		}
		return new PersonsResponse(list);
	}

	public List<PersonResponse> getDoctors(Integer idPerson) {

		Optional<Person> per = personRepo.findById(idPerson);
		if (!per.isPresent()) return new ArrayList<>();
		Person person = per.get();
		if (person.getProvince() == null) return new ArrayList<>();
		int province = person.getProvince().getId();
		Role role = roleRepo.findByName(Roles.PROFESIONAL_MEDICO.toString());
		List<Person> persons = patientDoctorRepo.getDoctors(province, role.getId());
		List<PersonResponse> list = new ArrayList<>();
		for (Person p : persons) list.add(new PersonResponse(p));
		return list;
	}

	public Person findByEmail(String email) {
		return accountRepo.findByEmail(email).getPerson();
	}

	public void assignDoctor(int patient, int doctor) {

		PatientDoctor p = patientDoctorRepo.getDoctor(patient);
		if (p != null) {
			patientDoctorRepo.delete(p);
		}
		PatientDoctorPk id = new PatientDoctorPk(patient, doctor);
		PatientDoctor pd = new PatientDoctor(id);
		patientDoctorRepo.save(pd);
	}

	public Person modify(String email, Person person) {
		Account account = accountRepo.findByEmail(email);

		Person personRecovered = personRepo.findByDocument(person.getDocument());

		if(personRecovered == null) {
			Location location = new Location();
			location.setLatitude(person.getLocation().getLatitude());
			location.setLongitude(person.getLocation().getLongitude());
			Location locationStored = locationRepo.save(location);

			Status status = statusRepo.findByName(person.getStatus().getName());

			personRecovered = new Person();
			personRecovered.setLocation(locationStored);
			personRecovered.setStatus(status);
		} else if(person.getLocation().getId() == null){
			Location location = new Location();
			location.setLatitude(person.getLocation().getLatitude());
			location.setLongitude(person.getLocation().getLongitude());
			Location locationStored = locationRepo.save(location);
			
			personRecovered.setLocation(locationStored);
		}

		personRecovered.setDocument(person.getDocument());
		personRecovered.setName(person.getName());
		personRecovered.setLastname(person.getLastname());
		personRecovered.setBirthDate(person.getBirthDate());
		personRecovered.setPhone(person.getPhone());
		personRecovered.setSex(person.getSex());
		personRecovered.setAddress(person.getAddress());
		personRecovered.setProvince(person.getProvince());

		if(personRecovered.getPersonForms() == null) {
			Set<Form> forms = formService.getDefaultForms();
			personRecovered.setPersonForms(forms);
		}else if(personRecovered.getPersonForms().isEmpty()) {
			Set<Form> forms = formService.getDefaultForms();
			for(Form form : forms) personRecovered.addForm(form);
		}

		Person personResult = personRepo.save(personRecovered);

		account.setPerson(personResult);
		accountRepo.save(account);

		return personResult;
	}

	public void delete(String email) {

		Account account = accountRepo.findByEmail(email);

		Person personToDelete = account.getPerson();

		account.setPerson(null);

		accountRepo.save(account);

		personToDelete.setAccounts(null);

		personRepo.save(personToDelete);

		personRepo.delete(personToDelete);
	}

}
