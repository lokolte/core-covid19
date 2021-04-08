package com.core.covid19.repos;

import com.core.covid19.models.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PatientDoctorRepo extends JpaRepository<PatientDoctor, Integer> {

    PatientDoctor getDoctor(int patient);
    List<Account> getDoctors(int province);
    Person getDoctorPatient(int patient);
    List<Object[]> getPatients(int doctor);
}
