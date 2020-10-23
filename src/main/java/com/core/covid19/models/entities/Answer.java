package com.core.covid19.models.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * The persistent class for the contact database table.
 * 
 */
@Entity
@Table(name="answer")
@Data
@NamedQuery(name="Answer.findAll", query="SELECT a FROM Answer a")
public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=700)
	@JsonIgnoreProperties("itemsForm")
	private Form form;

	@Column(name="answer_date", nullable=false)
	private Timestamp answerDate;

	@ManyToMany(mappedBy = "personAnswers")
	@JsonIgnoreProperties("personAnswers")
	@EqualsAndHashCode.Exclude
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<Person> answerPersons;

	@OneToMany(mappedBy = "answer")
	@JsonIgnoreProperties("answer")
	private Set<ItemsAnswer> answers;

	public Answer() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public Timestamp getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(Timestamp answerDate) {
		this.answerDate = answerDate;
	}

	public Set<Person> getAnswerPersons() {
		return answerPersons;
	}

	public void setAnswerPersons(Set<Person> answerPersons) {
		this.answerPersons = answerPersons;
	}

	public Set<ItemsAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<ItemsAnswer> answers) {
		this.answers = answers;
	}
}