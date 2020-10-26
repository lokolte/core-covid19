package com.core.covid19.models.responses;

import java.io.Serializable;
import java.util.List;

import com.core.covid19.models.entities.Hospital;
import com.core.covid19.models.entities.Person;

public class HospitalsResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Person person;

	private List<Hospital> hospitals;

    public HospitalsResponse(Person person, List<Hospital> hospitals) {
    	this.person = person;
    	this.hospitals = hospitals;
    }

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Hospital> getHospitals() {
		return hospitals;
	}

	public void setHospitals(List<Hospital> hospitals) {
		this.hospitals = hospitals;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}