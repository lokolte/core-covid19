package com.core.covid19.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.covid19.models.entities.Status;

public interface StatusRepo extends JpaRepository<Status, Integer>{
	
	Status findByName(String name);

}
