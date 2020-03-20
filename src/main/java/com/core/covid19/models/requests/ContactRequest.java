package com.core.covid19.models.requests;

import java.io.Serializable;
import java.sql.Timestamp;

public class ContactRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private Timestamp contactDate;

	private LocationRequest location;

	public Timestamp getContactDate() {
		return contactDate;
	}

	public void setContactDate(Timestamp contactDate) {
		this.contactDate = contactDate;
	}

	public LocationRequest getLocation() {
		return location;
	}

	public void setLocation(LocationRequest location) {
		this.location = location;
	}

}
