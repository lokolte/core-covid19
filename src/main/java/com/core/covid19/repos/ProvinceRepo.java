package com.core.covid19.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.covid19.models.entities.Province;

public interface ProvinceRepo extends JpaRepository<Province, Integer> {

    Province findByName(String name);
}
