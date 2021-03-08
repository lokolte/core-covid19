package com.core.covid19.repos;

import com.core.covid19.models.entities.HospitalDoctor;
import com.core.covid19.models.entities.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalDoctorRepo extends JpaRepository<HospitalDoctor, Integer>  {

    List<Hospital> getHospitalsDoctor(int doctor);
    List<HospitalDoctor> getAsignados(int doctor);
}
