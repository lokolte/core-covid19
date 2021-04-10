package com.core.covid19.repos;

import com.core.covid19.models.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;

import com.core.covid19.models.entities.Province;

import java.util.List;

public interface ProvinceRepo extends JpaRepository<Province, Integer> {

    Province findByName(String name);
    List<District> getDistricts(int id);
}
