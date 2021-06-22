package com.core.covid19.controllers;

import java.util.List;

import com.core.covid19.models.entities.Message;
import com.core.covid19.models.responses.*;
import com.core.covid19.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.covid19.authentication.util.JwtUtil;
import com.core.covid19.models.entities.Person;
import com.core.covid19.models.requests.PersonRequest;

@RestController
@RequestMapping("/persons")
public class PersonController {

	@Autowired
	private PersonService personService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private FormService formService;

	@Autowired
	private MessageService messageService;

	@GetMapping
	public List<Person> list(){
		return personService.findAll();
	}

	/**
	 * Obtiene la lista de pacientes de acuerdo al rol del usuario
	 * 1 - admin puede ver todos los pacientes
	 * 2 - coordinadores solo pueden ver los pacientes de su region
	 * 3 - medicos solo de los pacientes asignados a el
	 */
	@GetMapping(value="/patients")
	public PersonsResponse getPatients(@RequestHeader("Authorization") String authorization) {
		return personService.getPatients(jwtUtil.getEmailFromJwtToken(authorization));
	}

	/**
	 * Obtiene los datos de un paciente
	 * @param id	Id persona
	 */
	@GetMapping(value="/patients/{id}")
	public PatientForm get(@PathVariable("id") Integer id) {
		return personService.get(id);
	}

	@GetMapping(value="/{id}/patients")
	public PersonsResponse getPatientsDoctor(@PathVariable("id") Integer id) {
		return personService.getPatientsDoctor(id);
	}

	@GetMapping(value="/{id}/patients/{idPatient}/messages")
	public MessageResponse getMessages(@PathVariable("id") Integer id,
									   @PathVariable("idPatient") Integer idPatient) {
		return personService.getMessages(id, idPatient);
	}

	@GetMapping("/{id}")
	public PersonAnswersResponse listAnswersFromPatients(@PathVariable("id") Integer id) {
		return answerService.findAllByPersonId(id);
	}

	@GetMapping("/{id}/doctors")
	public List<PersonResponse> getDoctors(@PathVariable("id") Integer id) {
		return personService.getDoctors(id);
	}

	@PostMapping("/{id}/doctors/{doctor}")
	public void assignDoctor(@PathVariable("id") Integer id, @PathVariable("doctor") int data){
		personService.assignDoctor(id, data);
	}

	@GetMapping("/{idPerson}/forms/{idForm}/answers")
	public PersonAnswersResponse getAnswersForm(
			@PathVariable("idPerson") int idPerson,
			@PathVariable("idForm") int idForm) {

		return formService.getAnswersForm(idPerson, idForm);
	}

	@GetMapping(value="/my")
	public Person get(@RequestHeader("Authorization") String authorization){
		return personService.findByEmail(jwtUtil.getEmailFromJwtToken(authorization));
	}

	@PostMapping
	public Person insert(@RequestHeader("Authorization") String authorization, @RequestBody PersonRequest personRequest){
		return personService.insert(personRequest, jwtUtil.getEmailFromJwtToken(authorization));
	}

	@PutMapping
	public Person modify(@RequestHeader("Authorization") String authorization, @RequestBody Person person){
		return personService.modify(jwtUtil.getEmailFromJwtToken(authorization), person, null);
	}

	@DeleteMapping
	public void delete(@RequestHeader("Authorization") String authorization) {
		personService.delete(jwtUtil.getEmailFromJwtToken(authorization));
	}

}