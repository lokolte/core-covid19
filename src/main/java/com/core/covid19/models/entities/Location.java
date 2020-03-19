package com.core.covid19.models.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * The persistent class for the location database table.
 * 
 */
@Entity
@Table(name="location")
@Data
@NamedQuery(name="Location.findAll", query="SELECT l FROM Location l")
public class Location implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false)
	private double latitude;

	@Column(nullable=false)
	private double longitude;

	//bi-directional many-to-one association to Contact
	@OneToMany(mappedBy="location")
	@JsonIgnoreProperties("location")
	@EqualsAndHashCode.Exclude
	private List<Contact> contacts;

	//bi-directional many-to-one association to Person
	@OneToMany(mappedBy="location")
	@JsonIgnoreProperties("location")
	@EqualsAndHashCode.Exclude
	private List<Person> persons;

	public Location() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public List<Contact> getContacts() {
		return this.contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public Contact addContact(Contact contact) {
		getContacts().add(contact);
		contact.setLocation(this);

		return contact;
	}

	public Contact removeContact(Contact contact) {
		getContacts().remove(contact);
		contact.setLocation(null);

		return contact;
	}

	public List<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public Person addPerson(Person person) {
		getPersons().add(person);
		person.setLocation(this);

		return person;
	}

	public Person removePerson(Person person) {
		getPersons().remove(person);
		person.setLocation(null);

		return person;
	}

}