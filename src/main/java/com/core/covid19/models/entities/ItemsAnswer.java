package com.core.covid19.models.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * The persistent class for the contact database table.
 * 
 */
@Entity
@Table(name="items_answer")
@Data
@NamedQuery(name="ItemsAnswer.findAll", query="SELECT ia FROM ItemsAnswer ia")
public class ItemsAnswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=true, length=1500)
	private String answer_text;

	@ManyToOne
    @JoinColumn(name = "answer_id")
	@JsonIgnoreProperties("itemsAnswered")
	@EqualsAndHashCode.Exclude
    Answer answer;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @JsonIgnoreProperties({"forms","answers"})
    Item item;
    
    @ManyToOne
    @JoinColumn(name = "option_id")
    @JsonIgnoreProperties("items")
    Option optionAnswered;

	public ItemsAnswer() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnswer_text() {
		return answer_text;
	}

	public void setAnswer_text(String answer_text) {
		this.answer_text = answer_text;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Option getOptionAnswered() {
		return optionAnswered;
	}

	public void setOptionAnswered(Option optionAnswered) {
		this.optionAnswered = optionAnswered;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}