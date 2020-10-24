package com.core.covid19.models.responses;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.core.covid19.models.entities.Form;
import com.core.covid19.models.entities.ItemsAnswer;

public class AnswerItemResponse implements Serializable, Comparable<AnswerItemResponse> {

	private static final long serialVersionUID = 1L;
	
	private Integer id;

	private Form form;

	private Timestamp answerDate;

	private List<ItemsAnswer> answers;

    public AnswerItemResponse(Integer id, Form form, Timestamp answerDate, List<ItemsAnswer> answers) {
    	this.id = id;
    	this.form = form;
    	this.answerDate = answerDate;
        this.answers = answers;
    }

	@Override
	public int compareTo(AnswerItemResponse air){
		return this.getAnswerDate().compareTo(air.getAnswerDate());
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

	public List<ItemsAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<ItemsAnswer> answers) {
		this.answers = answers;
	}
}