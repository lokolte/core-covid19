package com.core.covid19.models.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;


/**
 * The persistent class for the person database table.
 * 
 */
@Entity
@Table(name="person")
@Data
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(unique=true, nullable=false, length=70)
	private String document;

	@Column(nullable=false, length=300)
	private String name;
	
	@Column(nullable=false, length=300)
	private String lastname;

	@Column(nullable=false, length=20)
	private String phone;

	@Column(nullable=false, length=10)
	private String sex;

	//bi-directional many-to-one association to Account
	@OneToMany(mappedBy="person")
	@JsonIgnoreProperties("person")
	@EqualsAndHashCode.Exclude
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<Account> accounts;
	
	@ManyToMany
	@JoinTable(name = "person_forms",
	joinColumns = @JoinColumn(name = "person_id"), 
	inverseJoinColumns = @JoinColumn(name = "form_id"))
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<Form> personForms;

	//bi-directional many-to-one association to Location
	@ManyToOne
	@JoinColumn(name="location", nullable=false)
	@JsonIgnoreProperties("persons")
	private Location location;

	//bi-directional many-to-one association to Status
	@ManyToOne
	@JoinColumn(name="status", nullable=false)
	@JsonIgnoreProperties("persons")
	private Status status;

	public Person() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Set<Form> getPersonForms() {
		return personForms;
	}

	public void setPersonForms(Set<Form> personForms) {
		this.personForms = personForms;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}