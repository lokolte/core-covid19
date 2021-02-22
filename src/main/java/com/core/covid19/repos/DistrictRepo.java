package com.core.covid19.repos;

import com.core.covid19.models.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepo extends JpaRepository<District, Integer>  {
}
