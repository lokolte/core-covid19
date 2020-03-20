package com.core.covid19.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.covid19.models.entities.Contact;

public interface ContactRepo extends JpaRepository<Contact, Integer>{

	List<Contact> findByPersonId1(Integer personId1);
	
	List<Contact> findByPersonId2(Integer personId2);

}
