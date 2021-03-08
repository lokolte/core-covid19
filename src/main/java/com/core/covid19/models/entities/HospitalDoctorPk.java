package com.core.covid19.models.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class HospitalDoctorPk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable=false)
    private Integer hospital;

    @Column(nullable=false)
    private Integer doctor;

    public HospitalDoctorPk() {
    }

    public HospitalDoctorPk(Integer doctor, Integer hospital) {
        this.hospital = hospital;
        this.doctor = doctor;
    }

    public Integer getHospital() {
        return hospital;
    }

    public void setHospital(Integer hospital) {
        this.hospital = hospital;
    }

    public Integer getDoctor() {
        return doctor;
    }

    public void setDoctor(Integer doctor) {
        this.doctor = doctor;
    }
}
