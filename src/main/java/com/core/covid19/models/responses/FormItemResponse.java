package com.core.covid19.models.responses;

import java.io.Serializable;
import java.util.List;

import com.core.covid19.models.entities.Form;
import com.core.covid19.models.entities.Item;

public class FormItemResponse implements Serializable, Comparable<FormItemResponse> {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String title;
	private String subtitle;
	private Integer orderLevel;
	private Boolean isDefault;
	private List<Item> itemsForm;

	public FormItemResponse() {
	}

	public FormItemResponse(Integer id, String title, String subtitle,
							Integer orderLevel, List<Item> itemsForm,
							Boolean isDefault) {
    	this.id = id;
    	this.title = title;
    	this.subtitle = subtitle;
    	this.orderLevel = orderLevel;
        this.itemsForm = itemsForm;
        this.isDefault = isDefault;
    }

	public FormItemResponse(Form f) {
		this.id = f.getId();
		this.title = f.getTitle();
		this.subtitle = f.getSubtitle();
		this.orderLevel = f.getOrderLevel();
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

	public Boolean getDefault() {
		return isDefault;
	}

	public void setDefault(Boolean aDefault) {
		isDefault = aDefault;
	}

	public void setItemsForm(List<Item> itemsForm) {
		this.itemsForm = itemsForm;
	}
}