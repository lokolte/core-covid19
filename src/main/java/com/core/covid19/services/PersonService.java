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
public class PersonService extends BaseService {

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
	private MessageService messageService;

	@Autowired
	PatientDoctorRepo patientDoctorRepo;

	@Autowired
	private RoleAccountRepo roleAccountRepo;

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

	public Person get(int id) {
		return personRepo.findById(id).get();
	}

	public PersonsResponse getPatientsDoctor(int id) {
		Optional<Person> per = personRepo.findById(id);
		if (per == null || !per.isPresent()) return new PersonsResponse();

		Person person = per.get();
		Account account = person.getAccounts().stream().collect(Collectors.toList()).get(0);
		List<String> roles = account.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList());

		List<PersonResponse> list = new ArrayList<>();
		List<Object[]> patiens = new ArrayList<>();

		if(roles.contains(Roles.ADMIN.name())) {
			patiens = patientDoctorRepo.getAdminPatients();
		} else if(roles.contains(Roles.COORDINADOR.name()) && person.getProvince().getId() != null) {
			patiens = patientDoctorRepo.getCoordinatorPatients(person.getId(), person.getProvince().getId());
		} else if(roles.contains(Roles.PROFESIONAL_MEDICO.name())) {
			patiens = patientDoctorRepo.getPatients(person.getId());
		}

		for (Object[] d : patiens) {
			Integer idPatient = (Integer) d[0];
			Person p = personRepo.getOne(idPatient);
			Person doctor = patientDoctorRepo.getDoctorPatient(idPatient);

			if(doctor != null) list.add(new PersonResponse(p, doctor.getId(), doctor.getName()));
			else list.add(new PersonResponse(p));
		}

		return new PersonsResponse(list);
	}

	public MessageResponse getMessages(int id, int idPatient) {

		Optional<Person> per = personRepo.findById(idPatient);
		if (per == null || !per.isPresent()) return new MessageResponse();
		Person person = per.get();
		return messageService.findAllMyMessagePerson(person);
	}

	/**
	 * Obtiene la lista de pacientes de acuerdo al rol del usuario
	 */
	public PersonsResponse getPatients(String email) {

		// Obtener datos del usuario logueado
		List<Person> persons = new ArrayList<>();
		Account account = accountRepo.findByEmail(email);
		if (account.getPerson() == null) return new PersonsResponse();

		if (isAdmin(account.getRoles())) {
			// Si es admin, retornamos todos los pacientes
			Role role = roleRepo.findByName(Roles.CIVIL.toString());
			List<Account> accounts = accountRepo.getAllByRole(role.getId());
			persons = accounts.stream().map(Account::getPerson).collect(Collectors.toList());

		} else if (isCoordinator(account.getRoles())) {
			// Si es coordinador, retornamos todos los pacientes de su Region
			Person person = account.getPerson();
			if (person.getProvince() == null) return new PersonsResponse();
			Role role = roleRepo.findByName(Roles.CIVIL.toString());
			List<Account> accounts = accountRepo.getAllByRoleAndProvince(role.getId(), person.getProvince().getId());
			persons = accounts.stream().map(Account::getPerson).collect(Collectors.toList());

		} else if (isDoctor(account.getRoles())) {
			// Si es medico, retornamos los pacientes asignados a el
			Person person = account.getPerson();
			persons = personRepo.getPatientsByDoctor(person.getId());
		}

		// Procesamos y retornamos la lista de pacientes
		List<PersonResponse> list = new ArrayList<PersonResponse>();
		for (Person p : persons) {
			Person doctor = patientDoctorRepo.getDoctorPatient(p.getId());
			String d = doctor == null ? "Sin asignar" : doctor.getName() + " " + doctor.getLastname();
			list.add(new PersonResponse(p, d));
		}
		return new PersonsResponse(list);
	}

	/**
	 * Obtiene la lista de medicos que corresponden con la region del paciente
	 */
	public List<PersonResponse> getDoctors(Integer idPerson) {

		// Obtener la region del paciente
		Optional<Person> per = personRepo.findById(idPerson);
		if (!per.isPresent()) return new ArrayList<>();
		Person person = per.get();
		if (person.getProvince() == null) return new ArrayList<>();
		int province = person.getProvince().getId();

		// Obtener los medicos de la provincia
		Role role = roleRepo.findByName(Roles.PROFESIONAL_MEDICO.toString());
		List<Account> accounts = accountRepo.getAllByRoleAndProvince(role.getId(), person.getProvince().getId());
		List<Person> doctors = accounts.stream().map(Account::getPerson).collect(Collectors.toList());

		// Procesamos y retornamos la lista de medicos
		List<PersonResponse> list = new ArrayList<>();
		for (Person p : doctors) list.add(new PersonResponse(p));
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

	public Person modify(String email, Person person, List<RoleResponse> roles) {
		
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

		if (roles != null) {
			account.getRoles();
			List<RoleAccount> rolesAccount = roleAccountRepo.getRolesByAccount(account.getId());
			for (RoleAccount ra : rolesAccount) {
				roleAccountRepo.delete(ra);
			}
			for (RoleResponse r : roles) {
				RoleAccountPk pk = new RoleAccountPk(account.getId(), r.getId());
				RoleAccount roleAccount = new RoleAccount(pk);
				roleAccountRepo.save(roleAccount);
			}
		}

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
