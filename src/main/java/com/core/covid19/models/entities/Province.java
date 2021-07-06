package com.core.covid19.models.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="province")
@Data
@NamedQuery(name="Province.findAll", query="SELECT p FROM Province p")
@NamedQuery(name="Province.getDistricts", query="SELECT p.districts FROM Province p WHERE p.id = :id")
public class Province implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer id;
	
	@Column(unique=true, nullable=true, length=10)
	private String code;

	@Column(nullable=false, length=100)
	private String name;

	@Column(nullable=true, length=100)
	private String capital;
	
	@OneToMany(mappedBy="province")
	@JsonIgnoreProperties("province")
	@EqualsAndHashCode.Exclude
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<Person> persons;

	@OneToMany(mappedBy="province")
	@JsonIgnoreProperties("province")
	@EqualsAndHashCode.Exclude
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<District> districts;

	@ManyToMany(mappedBy = "provincesDoctor")
	@JsonIgnoreProperties("provincesDoctor")
	@EqualsAndHashCode.Exclude
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<Person> provincesDoctor;
	
	public Province() {
	}

	public Province(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}
	
	public List<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public List<District> getDistricts() {
		return districts;
	}

	public void setDistricts(List<District> districts) {
		this.districts = districts;
	}

	public Set<Person> getProvincesDoctor() {
		return provincesDoctor;
	}

	public void setProvincesDoctor(Set<Person> provincesDoctor) {
		this.provincesDoctor = provincesDoctor;
	}
}
