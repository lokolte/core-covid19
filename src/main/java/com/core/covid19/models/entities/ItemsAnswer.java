package com.core.covid19.models.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
	private String answerText;

	@ManyToOne
    @JoinColumn(name = "answer_id")
	@JsonIgnoreProperties("answers")
	@EqualsAndHashCode.Exclude
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @JsonIgnoreProperties({"forms","answers"})
    private Item item;

	public ItemsAnswer() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
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
}