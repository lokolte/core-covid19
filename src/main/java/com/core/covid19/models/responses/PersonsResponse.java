package com.core.covid19.models.responses;

import java.util.ArrayList;
import java.util.List;

public class PersonsResponse {

private static final long serialVersionUID = 1L;
	
	private final List<PersonResponse> persons;
	
	public PersonsResponse() {
		this.persons = new ArrayList<PersonResponse>();
	}
	
	public PersonsResponse(List<PersonResponse> persons) {
		this.persons = persons;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<PersonResponse> getPersons() {
		return persons;
	}
}
