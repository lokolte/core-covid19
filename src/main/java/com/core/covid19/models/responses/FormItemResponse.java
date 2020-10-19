package com.core.covid19.models.responses;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;

import com.core.covid19.models.entities.Form;
import com.core.covid19.models.entities.Item;

public class FormItemResponse implements Serializable, Comparable<FormItemResponse> {

	private static final long serialVersionUID = 1L;
	
	private final Integer id;

	private final String title;

	private final String subtitle;

	private final List<Item> itemsForm;

    public FormItemResponse(Integer id, String title, String subtitle, List<Item> itemsForm) {
    	this.id = id;
    	this.title = title;
    	this.subtitle = subtitle;
        this.itemsForm = itemsForm;
    }

	@Override
	public int compareTo(FormItemResponse fir){
		return this.getId().compareTo(fir.getId());
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public List<Item> getItemsForm() {
		return itemsForm;
	}
}