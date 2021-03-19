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
 * The persistent class for the contact database table.
 * 
 */
@Entity
@Table(name="form")
@Data
@NamedQuery(name="Form.findAll", query="SELECT f FROM Form f")
public class Form implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=700)
	private String title;

	@Column(nullable=true, length=1500)
	private String subtitle;

	@Column(nullable=true, length=1500)
	private String description;

	@Column(name="order_level", nullable=false)
	private Integer orderLevel;

	@ManyToMany
	@JoinTable(name = "form_items",
	joinColumns = @JoinColumn(name = "form_id"), 
	inverseJoinColumns = @JoinColumn(name = "item_id"))
	@EqualsAndHashCode.Exclude
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<Item> itemsForm;

	@ManyToMany(mappedBy = "personForms")
	@JsonIgnoreProperties("personForms")
	@EqualsAndHashCode.Exclude
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<Person> formPersons;

	//bi-directional many-to-one association to Account
	@OneToMany(mappedBy="form")
	@JsonIgnoreProperties("form")
	@EqualsAndHashCode.Exclude
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<Answer> answers;

	public Form() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public Integer getOrderLevel() {
		return orderLevel;
	}

	public void setOrderLevel(Integer orderLevel) {
		this.orderLevel = orderLevel;
	}

	public Set<Item> getItemsForm() {
		return itemsForm;
	}

	public void setItemsForm(Set<Item> itemsForm) {
		this.itemsForm = itemsForm;
	}

	public Set<Person> getFormPersons() {
		return formPersons;
	}

	public void setFormPersons(Set<Person> formPersons) {
		this.formPersons = formPersons;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}