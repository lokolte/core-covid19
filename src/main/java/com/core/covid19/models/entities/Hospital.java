package com.core.covid19.models.entities;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * The persistent class for the person database table.
 * 
 */
@Entity
@Table(name="hospital")
@Data
@NamedQuery(name="Hospital.findAll", query="SELECT h FROM Hospital h")
@NamedQuery(name="Hospital.findAByProvince",
		query="SELECT h from Hospital h, District d WHERE h.district.id = d.id and d.province.id = :province")
public class Hospital implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=300)
	private String name;

	@Column(nullable=false, length=1000)
	private String code;

	@Column(nullable=false, length=1000)
	private String address;

	@Column(nullable=true)
	private Boolean state;

	@Column(nullable=true, length=50)
	private String phone;

	@Column(nullable=false, length=15)
	private String area;

	@Column(nullable=true, length=100)
	private String director;

	@Column(nullable=false, length=100)
	private String type;

	//bi-directional many-to-one association to Location
	@ManyToOne
	@JoinColumn(name="location", nullable=true)
	@JsonIgnoreProperties("hospitals")
	private Location location;

	@ManyToOne
	@JoinColumn(name="district", nullable=true)
	@JsonIgnoreProperties("hospitals")
	private District district;

	public Hospital() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}
}