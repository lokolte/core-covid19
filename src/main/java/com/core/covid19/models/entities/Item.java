package com.core.covid19.models.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.core.covid19.models.responses.QuestionResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * The persistent class for the contact database table.
 * 
 */
@Entity
@Table(name="item")
@Data
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
public class Item implements Serializable, Comparable<Item> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=700)
	private String title;

	@Column(nullable=true, length=1500)
	private String subtitle;

	@Column(nullable=false, length=20)
	private String type;

	@Column(name="order_level", nullable=false)
	private Integer orderLevel;

	@ManyToMany
	@JoinTable(name = "item_options",
	joinColumns = @JoinColumn(name = "item_id"), 
	inverseJoinColumns = @JoinColumn(name = "option_id"))
	private Set<Option> optionsItem;
	
	@ManyToMany(mappedBy = "itemsForm")
	@JsonIgnoreProperties({"itemsForm","item"})
	@EqualsAndHashCode.Exclude
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<Form> forms;
	
	@OneToMany(mappedBy = "item")
	@JsonIgnoreProperties({"itemsForm","item"})
	@EqualsAndHashCode.Exclude
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<ItemsAnswer> answers;

	public Item() {
	}

	public Item(Integer id, String title, String subtitle, Integer orderLevel, String type) {
		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
		this.orderLevel = orderLevel;
		this.type = type;
	}

	public Item(QuestionResponse data) {
		this.id = data.getId();
		this.title = data.getTitle();
		this.subtitle = data.getSubtitle();
		this.orderLevel = data.getOrderLevel();
		this.type = data.getType();
	}
	
	@Override
	public int compareTo(Item i){
		return this.getOrderLevel().compareTo(i.getOrderLevel());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getOrderLevel() {
		return orderLevel;
	}

	public void setOrderLevel(Integer orderLevel) {
		this.orderLevel = orderLevel;
	}

	public Set<Option> getOptionsItem() {
		return optionsItem;
	}

	public void setOptionsItem(Set<Option> optionsItem) {
		this.optionsItem = optionsItem;
	}

	public Set<Form> getForms() {
		return forms;
	}

	public void setForms(Set<Form> forms) {
		this.forms = forms;
	}

	public Set<ItemsAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<ItemsAnswer> answers) {
		this.answers = answers;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}