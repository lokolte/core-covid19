package com.core.covid19.repos;

import com.core.covid19.models.entities.Message;
import com.core.covid19.models.entities.PatientDoctor;
import com.core.covid19.models.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientDoctorRepo extends JpaRepository<PatientDoctor, Integer> {

    PatientDoctor getDoctor(int patient);
    List<Person> getDoctors(int province, int role);
    Person getDoctorPatient(int patient);
    List<Object[]> getPatients(int doctor);
}
