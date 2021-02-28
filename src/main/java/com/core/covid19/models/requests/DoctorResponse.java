package com.core.covid19.models.requests;

import com.core.covid19.models.entities.Account;
import com.core.covid19.models.entities.Person;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DoctorResponse implements Serializable {

    private int id;
    private String name;
    private String lastname;
    private String phone;
    private String status;
    private String address;
    private String birthDate;
    private String sex;
    private String document;
    private String province;
    private String email;
    private double latitude;
    private double longitude;

    public DoctorResponse(Person p) {

        this.id = p.getId();
        this.name = p.getName();
        this.lastname = p.getLastname();
        this.phone = p.getPhone();
        this.document = p.getDocument();
        this.address = p.getAddress();
        this.sex = p.getSex();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        if (p.getBirthDate() != null) {
            try {
                this.birthDate = format.format(p.getBirthDate());
            } catch (Exception e) {}
        }
        if (p.getLocation() != null) {
            this.latitude = p.getLocation().getLatitude();
            this.longitude = p.getLocation().getLongitude();
        }
        if (p.getAccounts() != null && p.getAccounts().size() > 0) {
            List<Account> accounts = new ArrayList<>();
            accounts.addAll(p.getAccounts());
            this.email = accounts.get(0).getEmail();
        }
        if (p.getProvince() != null) {
            this.province = p.getProvince().getName();
        }
        if (p.getStatus() != null) {
            this.status = p.getStatus().getName();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
