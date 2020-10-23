package com.core.covid19.models.entities;

import java.io.Serializable;
import javax.persistence.*;

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
@Table(name="option")
@Data
@NamedQuery(name="Option.findAll", query="SELECT o FROM Option o")
public class Option implements Serializable {
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
	
	@ManyToMany(mappedBy = "optionsItem")
	@JsonIgnoreProperties("optionsItem")
	@EqualsAndHashCode.Exclude
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<Item> optionItems;

	public Option() {
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

	public Set<Item> getOptionItems() {
		return optionItems;
	}

	public void setOptionItems(Set<Item> optionItems) {
		this.optionItems = optionItems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}