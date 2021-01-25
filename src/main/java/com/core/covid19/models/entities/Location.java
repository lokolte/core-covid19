package com.core.covid19.models.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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

	//bi-directional many-to-one association to Person
	@OneToMany(mappedBy="location")
	@JsonIgnoreProperties("location")
	@EqualsAndHashCode.Exclude
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<Person> persons;

	//bi-directional many-to-one association to Person
	@OneToMany(mappedBy="location")
	@JsonIgnoreProperties("location")
	@EqualsAndHashCode.Exclude
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<Hospital> hospitals;

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

	public List<Hospital> getHospitals() {
		return this.hospitals;
	}

	public void setHospitals(List<Hospital> hospitals) {
		this.hospitals = hospitals;
	}

	public Hospital addHospital(Hospital hospital) {
		getHospitals().add(hospital);
		hospital.setLocation(this);

		return hospital;
	}

	public Hospital removeHospital(Hospital hospital) {
		getHospitals().remove(hospital);
		hospital.setLocation(null);

		return hospital;
	}
}