package com.core.covid19.models.responses;

import java.io.Serializable;
import java.util.List;

import com.core.covid19.models.entities.Item;

public class FormItemResponse implements Serializable, Comparable<FormItemResponse> {

	private static final long serialVersionUID = 1L;
	
	private final Integer id;

	private final String title;

	private final String subtitle;
	
	private final Integer order;

	private final List<Item> itemsForm;

    public FormItemResponse(Integer id, String title, String subtitle, Integer order, List<Item> itemsForm) {
    	this.id = id;
    	this.title = title;
    	this.subtitle = subtitle;
    	this.order = order;
        this.itemsForm = itemsForm;
    }

	@Override
	public int compareTo(FormItemResponse fir){
		return this.getOrder().compareTo(fir.getOrder());
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

	public Integer getOrder() {
		return order;
	}

	public List<Item> getItemsForm() {
		return itemsForm;
	}
}