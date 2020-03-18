package com.core.covid19.models;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import java.sql.Timestamp;


/**
 * The persistent class for the contact database table.
 * 
 */
@Entity
@Table(name="contact")
@Data
@NamedQuery(name="Contact.findAll", query="SELECT c FROM Contact c")
public class Contact implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="contact_date", nullable=false)
	private Timestamp contactDate;

	@Column(name="person_id1", nullable=false)
	private Integer personId1;

	@Column(name="person_id2")
	private Integer personId2;

	//bi-directional many-to-one association to Location
	@ManyToOne//(fetch=FetchType.LAZY)
	@JoinColumn(name="contact_location")
	@JsonIgnoreProperties("contacts")
	private Location location;

	public Contact() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getContactDate() {
		return this.contactDate;
	}

	public void setContactDate(Timestamp contactDate) {
		this.contactDate = contactDate;
	}

	public Integer getPersonId1() {
		return this.personId1;
	}

	public void setPersonId1(Integer personId1) {
		this.personId1 = personId1;
	}

	public Integer getPersonId2() {
		return this.personId2;
	}

	public void setPersonId2(Integer personId2) {
		this.personId2 = personId2;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}