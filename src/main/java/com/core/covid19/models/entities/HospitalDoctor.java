package com.core.covid19.models.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="hospital_doctor")
@NamedQuery(name="HospitalDoctor.getHospitalsDoctor",
        query="SELECT h FROM HospitalDoctor hd, Hospital h WHERE hd.id.doctor = :doctor AND hd.id.hospital = h.id ")
@NamedQuery(name="HospitalDoctor.getAsignados",
        query="SELECT hd FROM HospitalDoctor hd WHERE hd.id.doctor = :doctor ")
public class HospitalDoctor {

    @EmbeddedId
    private HospitalDoctorPk id;

    public HospitalDoctor() {
    }

    public HospitalDoctor(HospitalDoctorPk id) {
        this.id = id;
    }

    public HospitalDoctorPk getId() {
        return id;
    }

    public void setId(HospitalDoctorPk id) {
        this.id = id;
    }
}
