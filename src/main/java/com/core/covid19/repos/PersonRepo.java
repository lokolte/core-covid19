package com.core.covid19.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.covid19.models.entities.Person;

public interface PersonRepo extends JpaRepository<Person, Integer> {
	
	Person findByDocument(String document);
	List<Person> getPatients(int province);
	List<Person> getDoctors(int role);
}
