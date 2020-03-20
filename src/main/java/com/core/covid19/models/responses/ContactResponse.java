package com.core.covid19.models.responses;

import java.io.Serializable;
import java.sql.Timestamp;

import com.core.covid19.models.entities.Location;
import com.core.covid19.models.entities.Person;

public class ContactResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Timestamp contactDate;

	private Person personContacted;

	private Location location;

	public ContactResponse() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getContactDate() {
		return contactDate;
	}

	public void setContactDate(Timestamp contactDate) {
		this.contactDate = contactDate;
	}

	public Person getPersonContacted() {
		return personContacted;
	}

	public void setPersonContacted(Person personContacted) {
		this.personContacted = personContacted;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}