package com.core.covid19.models.responses;

import java.io.Serializable;

import com.core.covid19.models.entities.Person;

public class PersonResponse implements Serializable {

	private int id;
	private String name;
	private String phone;

	public PersonResponse(int id) {
		this.id = id;
	}
	
	public PersonResponse(Person p) {
		this.id = p.getId();
		this.name = p.getName() + " " + p.getLastname();
		this.phone = p.getPhone();
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
}
