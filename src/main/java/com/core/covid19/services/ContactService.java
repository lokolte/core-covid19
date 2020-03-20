package com.core.covid19.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.covid19.models.entities.Account;
import com.core.covid19.models.entities.Contact;
import com.core.covid19.models.entities.Location;
import com.core.covid19.models.entities.Person;
import com.core.covid19.models.requests.ContactRequest;
import com.core.covid19.models.responses.ContactResponse;
import com.core.covid19.repos.AccountRepo;
import com.core.covid19.repos.ContactRepo;
import com.core.covid19.repos.LocationRepo;
import com.core.covid19.repos.PersonRepo;

@Service
public class ContactService {

	@Autowired
	private ContactRepo contactRepo;

	@Autowired
	private LocationRepo locationRepo;

	@Autowired
	private PersonRepo personRepo;

	@Autowired
	private AccountRepo accountRepo;

	public List<ContactResponse> findAllByDocument(String email) {

		Account account = accountRepo.findByEmail(email);

		Person person = account.getPerson();

		List<Contact> contactsAsContactor = contactRepo.findByPersonId1(person.getId());

		List<Contact> contactsAsContacted = contactRepo.findByPersonId2(person.getId());

		List<ContactResponse> contactsResponse = new ArrayList<ContactResponse>();

		if (contactsAsContactor != null)
			for (Contact contact : contactsAsContactor) {
				ContactResponse contactResponse = new ContactResponse();
				contactResponse.setId(contact.getId());
				contactResponse.setContactDate(contact.getContactDate());
				contactResponse.setPersonContacted(personRepo.findById(contact.getPersonId2()).orElse(null));
				contactResponse.setLocation(contact.getLocation());

				contactsResponse.add(contactResponse);
			}

		if (contactsAsContacted != null)
			for (Contact contact : contactsAsContacted) {
				ContactResponse contactResponse = new ContactResponse();
				contactResponse.setId(contact.getId());
				contactResponse.setContactDate(contact.getContactDate());
				contactResponse.setPersonContacted(personRepo.findById(contact.getPersonId1()).orElse(null));
				contactResponse.setLocation(contact.getLocation());

				contactsResponse.add(contactResponse);
			}

		return contactsResponse;
	}

	public ContactResponse insert(String document, String email, ContactRequest contactRequest) {
		Account account = accountRepo.findByEmail(email);

		Person personContactor = account.getPerson();
		Person personContacted = personRepo.findByDocument(document);

		Contact contact = new Contact();
		contact.setContactDate(contactRequest.getContactDate());
		contact.setPersonId1(personContactor.getId());
		contact.setPersonId2(personContacted.getId());

		Location location = null;
		Location locationStored = null;

		if (contactRequest.getLocation() != null) {
			location = new Location();
			location.setLatitude(contactRequest.getLocation().getLatitude());
			location.setLongitude(contactRequest.getLocation().getLongitude());
			locationStored = locationRepo.save(location);
		}

		contact.setLocation(locationStored);

		Contact contactStored = contactRepo.save(contact);

		ContactResponse contactResponse = new ContactResponse();
		contactResponse.setId(contactStored.getId());
		contactResponse.setContactDate(contactStored.getContactDate());
		contactResponse.setPersonContacted(personContacted);
		contactResponse.setLocation(contactStored.getLocation());

		return contactResponse;
	}

}
