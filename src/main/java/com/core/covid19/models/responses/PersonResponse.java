package com.core.covid19.models.responses;

import java.io.Serializable;

import com.core.covid19.models.entities.Person;

public class PersonResponse implements Serializable {

	private int id;
	private String name;
	private String phone;
	private String doctor;
	
	public PersonResponse(Person p) {
		this.id = p.getId();
		this.name = p.getName() + " " + p.getLastname();
		this.phone = p.getPhone();
	}

	public PersonResponse(Person p, String doctor) {
		this.id = p.getId();
		this.name = p.getName() + " " + p.getLastname();
		this.phone = p.getPhone();
		this.doctor = doctor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
}
