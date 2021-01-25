package com.core.covid19.models.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PatientDoctorPk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable=false)
    private Integer patient;

    @Column(nullable=false)
    private Integer doctor;

    public PatientDoctorPk() {
    }

    public PatientDoctorPk(Integer patient, Integer doctor) {
        this.patient = patient;
        this.doctor = doctor;
    }

    public Integer getPatient() {
        return patient;
    }

    public void setPatient(Integer patient) {
        this.patient = patient;
    }

    public Integer getDoctor() {
        return doctor;
    }

    public void setDoctor(Integer doctor) {
        this.doctor = doctor;
    }
}
