package com.core.covid19.models.responses;

import java.io.Serializable;
import java.util.List;

public class PersonFormsResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final List<FormItemResponse> forms;

    public PersonFormsResponse(List<FormItemResponse> forms) {
        this.forms = forms;
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<FormItemResponse> getForms() {
		return forms;
	}
}