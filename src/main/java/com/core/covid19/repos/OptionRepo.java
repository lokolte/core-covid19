package com.core.covid19.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.covid19.models.entities.Option;

public interface OptionRepo extends JpaRepository<Option, Integer>{
}
