package com.core.covid19.models.entities;

import java.io.Serializable;
import java.sql.Timestamp;

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
@NamedQuery(name="Person.getPatients", query="SELECT p FROM Person p, Account a "
		+ "WHERE a.person.id = p.id and a.role.id = 1 and p.province.id = :province")
@NamedQuery(name="Person.getDoctors", query="SELECT p FROM Person p, Account a " +
		"WHERE a.person.id = p.id and a.role.id = :role")
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

	@Column(name="birth_date", nullable=false)
	private Timestamp birthDate;

	@Column(nullable=false, length=20)
	private String phone;

	@Column(nullable=false, length=10)
	private String sex;

	@Column(nullable=false, length=1000)
	private String address;

	//bi-directional many-to-one association to Account
	@OneToMany(mappedBy="person")
	@JsonIgnoreProperties("person")
	@EqualsAndHashCode.Exclude
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<Account> accounts;
	
	@ManyToMany
	@JoinTable(name = "person_forms",
	joinColumns = @JoinColumn(name = "person_id"), 
	inverseJoinColumns = @JoinColumn(name = "form_id"))
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<Form> personForms;

	@ManyToMany
	@JoinTable(name = "person_answers",
	joinColumns = @JoinColumn(name = "person_id"), 
	inverseJoinColumns = @JoinColumn(name = "answer_id"))
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<Answer> personAnswers;

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
	
	@ManyToOne
	@JoinColumn(name="province", nullable=true)
	@JsonIgnoreProperties("persons")
	private Province province;

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

	public Timestamp getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Timestamp birthDate) {
		this.birthDate = birthDate;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public Set<Form> getPersonForms() {
		return personForms;
	}

	public void setPersonForms(Set<Form> personForms) {
		this.personForms = personForms;
	}
	
	public void addForm(Form form) {
        this.personForms.add(form);
        form.getFormPersons().add(this);
    }
 
    public void removeForm(Form form) {
        this.personForms.remove(form);
        form.getFormPersons().remove(this);
    }

	public Set<Answer> getPersonAnswers() {
		return personAnswers;
	}

	public void setPersonAnswers(Set<Answer> personAnswers) {
		this.personAnswers = personAnswers;
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

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}
}