package com.core.covid19.models.entities;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="patient_doctor")
@Data
@NamedQuery(name="PatientDoctor.getDoctor",
        query="SELECT p FROM PatientDoctor p WHERE p.id.patient = :patient ")
@NamedQuery(name="PatientDoctor.getDoctors",
        query="SELECT p FROM Person p, Account a WHERE p.id = a.id AND a.role.id = :role AND p.province.id = :province")
@NamedQuery(name="PatientDoctor.getDoctorPatient",
        query="SELECT p FROM Person p, PatientDoctor pd WHERE pd.id.patient = :patient AND p.id = pd.id.doctor")
@NamedQuery(name="PatientDoctor.getPatients",
        query="SELECT p.id, (select max(m.sendDate) from Message m where m.personSenderId = p.id OR m.personReceivedId = p.id) "
                + "FROM Person p, PatientDoctor pd WHERE pd.id.patient = p.id AND pd.id.doctor = :doctor "
                + "AND p.id IN (SELECT m.personSenderId FROM Message m) ORDER BY 2 DESC")
public class PatientDoctor {

    @EmbeddedId
    private PatientDoctorPk id;

    public PatientDoctor() {
    }

    public PatientDoctor(PatientDoctorPk id) {
        this.id = id;
    }

    public PatientDoctorPk getId() {
        return id;
    }

    public void setId(PatientDoctorPk id) {
        this.id = id;
    }
}
