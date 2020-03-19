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
import com.core.covid19.models.requests.PersonRequest;
import com.core.covid19.services.PersonService;

@RestController
@RequestMapping("/persons")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@GetMapping
	public List<Person> list(){
		return personService.findAll();
	}
	
	@GetMapping(value = "/{document}")
	public Person get(@PathVariable("document") String document){
		return personService.findByDocument(document);
	}

	@PostMapping
	public Person insert(@RequestBody PersonRequest personRequest){
		return personService.insertPerson(personRequest);
	}
	
	@PutMapping
	public void modify(@RequestBody Person person){
		personService.save(person);
	}
	
	@DeleteMapping(value = "/{document}")
	public void delete(@PathVariable("document") String document) {
		personService.delete(document);
	}

}