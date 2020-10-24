package com.core.covid19.models.responses;

import java.io.Serializable;
import java.util.List;

public class PersonAnswersResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final List<AnswerItemResponse> answers;

    public PersonAnswersResponse(List<AnswerItemResponse> answers) {
        this.answers = answers;
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<AnswerItemResponse> getAnswers() {
		return answers;
	}
}