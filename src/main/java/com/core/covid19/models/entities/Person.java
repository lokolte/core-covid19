package com.core.covid19.models.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


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

	@Column(nullable=false, length=70)
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
	private List<Account> accounts;

	//bi-directional many-to-one association to Location
	@ManyToOne//(fetch=FetchType.LAZY)
	@JoinColumn(name="location", nullable=false)
	@JsonIgnoreProperties("persons")
	private Location location;

	//bi-directional many-to-one association to Status
	@ManyToOne//(fetch=FetchType.LAZY)
	@JoinColumn(name="status", nullable=false)
	@JsonIgnoreProperties("persons")
	private Status status;

	public Person() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDocument() {
		return this.document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public List<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Account addAccount(Account account) {
		getAccounts().add(account);
		account.setPerson(this);

		return account;
	}

	public Account removeAccount(Account account) {
		getAccounts().remove(account);
		account.setPerson(null);

		return account;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location locationBean) {
		this.location = locationBean;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status statusBean) {
		this.status = statusBean;
	}

}