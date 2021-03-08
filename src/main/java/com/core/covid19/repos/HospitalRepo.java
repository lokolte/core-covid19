package com.core.covid19.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.covid19.models.entities.Hospital;

import java.util.List;

public interface HospitalRepo extends JpaRepository<Hospital, Integer> {

    List<Hospital> findAByProvince(int province);
}