package com.core.covid19.models.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@Table(name="account")
@Data
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
@NamedQuery(name="Account.getAccountByPersonId", query="SELECT a FROM Person p, Account a "
		+ "WHERE a.person.id = p.id and p.id = :person")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(unique=true, nullable=false, length=100)
	private String email;

	@Column(nullable=false, length=100)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_id")
	@JsonIgnoreProperties("accounts")
	private Person person;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="role_id")
	@JsonIgnoreProperties("accounts")
	private Role role;

	public Account() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}