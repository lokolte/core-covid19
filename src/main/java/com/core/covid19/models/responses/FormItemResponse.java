package com.core.covid19.models.responses;

import java.io.Serializable;
import java.util.List;

import com.core.covid19.models.entities.Item;

public class FormItemResponse implements Serializable, Comparable<FormItemResponse> {

	private static final long serialVersionUID = 1L;
	
	private final Integer id;

	private final String title;

	private final String subtitle;
	
	private final Integer orderLevel;

	private final List<Item> itemsForm;

    public FormItemResponse(Integer id, String title, String subtitle, Integer orderLevel, List<Item> itemsForm) {
    	this.id = id;
    	this.title = title;
    	this.subtitle = subtitle;
    	this.orderLevel = orderLevel;
        this.itemsForm = itemsForm;
    }

	@Override
	public int compareTo(FormItemResponse fir){
		return this.getOrderLevel().compareTo(fir.getOrderLevel());
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public Integer getOrderLevel() {
		return orderLevel;
	}

	public List<Item> getItemsForm() {
		return itemsForm;
	}
}