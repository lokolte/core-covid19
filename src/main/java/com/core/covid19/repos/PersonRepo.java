package com.core.covid19.repos;

import java.util.List;

import com.core.covid19.models.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import com.core.covid19.models.entities.Person;

public interface PersonRepo extends JpaRepository<Person, Integer> {
	
	Person findByDocument(String document);
	//List<Account> getPatients(int province);
	List<Account> getAccounts();
	List<Person> getPatientsByDoctor(int doctor);
}
