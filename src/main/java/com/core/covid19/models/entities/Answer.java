package com.core.covid19.models.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

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

	@OneToMany(mappedBy = "answer")
	@JsonIgnoreProperties("answer")
	Set<ItemsAnswer> itemsAnswered;

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

	public Set<ItemsAnswer> getItemsAnswered() {
		return itemsAnswered;
	}

	public void setItemsAnswered(Set<ItemsAnswer> itemsAnswered) {
		this.itemsAnswered = itemsAnswered;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}