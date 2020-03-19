package com.core.covid19.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.covid19.models.entities.Person;
import com.core.covid19.repos.PersonRepo;

@RestController
@RequestMapping("/persons")
public class PersonController {

	@Autowired
	private PersonRepo personRepo;
	
	@GetMapping
	public List<Person> list(){
		return personRepo.findAll();
	}
	
	@GetMapping(value = "/{document}")
	public Person get(@PathVariable("document") String document){
		return personRepo.findByDocument(document);
	}

	@PostMapping
	public void insert(@RequestBody Person person){
		personRepo.save(person);
	}
	
	@PutMapping
	public void modify(@RequestBody Person person){
		personRepo.save(person);
	}
	
	@DeleteMapping(value = "/{document}")
	public void delete(@PathVariable("document") String document) {
		Person personToDelete = personRepo.findByDocument(document);
		personRepo.delete(personToDelete);
	}

}