package com.core.covid19.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.covid19.models.entities.Status;
import com.core.covid19.models.enums.PersonStatus;
import com.core.covid19.models.entities.Account;
import com.core.covid19.models.entities.Contact;
import com.core.covid19.models.entities.Form;
import com.core.covid19.models.entities.Location;
import com.core.covid19.models.entities.Person;
import com.core.covid19.models.requests.PersonRequest;
import com.core.covid19.repos.AccountRepo;
import com.core.covid19.repos.ContactRepo;
import com.core.covid19.repos.LocationRepo;
import com.core.covid19.repos.PersonRepo;
import com.core.covid19.repos.StatusRepo;
import com.core.covid19.services.FormService;

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
	private ContactRepo contactRepo;
	
	@Autowired
	private FormService formService;

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

	public Person findByEmail(String email) {
		return accountRepo.findByEmail(email).getPerson();
	}

	public Person modify(String email, Person person) {
		if (person.getStatus().getName().equals(PersonStatus.INFECTED.toString())) {
			List<Contact> contactsAsContactor = contactRepo.findByPersonId1(person.getId());
			List<Contact> contactsAsContacted = contactRepo.findByPersonId2(person.getId());

			Status statusInfected = statusRepo.findByName(PersonStatus.INFECTED.toString());
			Status statusSuspect = statusRepo.findByName(PersonStatus.SUSPECT.toString());

			if (contactsAsContactor != null)
				for (Contact contact : contactsAsContactor) {
					Person personContacted = personRepo.findById(contact.getPersonId2()).orElse(null);
					Status statusContacted = personContacted.getStatus();
					if (statusContacted.getName().equals(PersonStatus.HEALTHY.toString())) {
						personContacted.setStatus(statusSuspect);
						personRepo.save(personContacted);
					}
				}

			if (contactsAsContacted != null)
				for (Contact contact : contactsAsContacted) {
					Person personContactor = personRepo.findById(contact.getPersonId2()).orElse(null);
					Status statusContactor = personContactor.getStatus();
					if (statusContactor.getName().equals(PersonStatus.HEALTHY.toString())) {
						personContactor.setStatus(statusSuspect);
						personRepo.save(personContactor);
					}
				}

			person.setStatus(statusInfected);
		}

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
		}

		personRecovered.setDocument(person.getDocument());
		personRecovered.setName(person.getName());
		personRecovered.setLastname(person.getLastname());
		personRecovered.setBirthDate(person.getBirthDate());
		personRecovered.setPhone(person.getPhone());
		personRecovered.setSex(person.getSex());
		personRecovered.setAddress(person.getAddress());

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
