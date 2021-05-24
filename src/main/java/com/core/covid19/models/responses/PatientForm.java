package com.core.covid19.models.responses;

import com.core.covid19.models.entities.*;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PatientForm {

    private Integer id;
    private String document;
    private String name;
    private String lastname;
    private Timestamp birthDate;
    private String phone;
    private String sex;
    private String address;
    private Location location;
    private Status status;
    private Province province;
    private List<FormItemResponse> forms;

    public PatientForm() {
    }

    public PatientForm(Person p, Set<Form> list) {
        this.id = p.getId();
        this.document = p.getDocument();
        this.name = p.getName();
        this.lastname = p.getLastname();
        this.birthDate = p.getBirthDate();
        this.phone = p.getPhone();
        this.sex = p.getSex();
        this.address = p.getAddress();
        this.location = p.getLocation();
        this.status = p.getStatus();
        this.province = p.getProvince();
        this.forms = new ArrayList<>();
        for (Form f : list) {
            this.forms.add(new FormItemResponse(f));
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Timestamp getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public List<FormItemResponse> getForms() {
        return forms;
    }

    public void setForms(List<FormItemResponse> forms) {
        this.forms = forms;
    }
}
