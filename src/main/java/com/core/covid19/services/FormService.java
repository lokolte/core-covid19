package com.core.covid19.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.covid19.models.entities.Account;
import com.core.covid19.models.entities.Form;
import com.core.covid19.models.entities.Person;
import com.core.covid19.repos.AccountRepo;
import com.core.covid19.repos.FormRepo;

@Service
public class FormService {

	@Autowired
	private FormRepo formRepo;
	
	@Autowired
	private AccountRepo accountRepo;

	public Set<Form> findAllByPersonEmail(String email) {
		Account account = accountRepo.findByEmail(email);

		Person person = account.getPerson();

		return person.getPersonForms();
	}

	public void addDefaultFormsToPerson(String email) {
		Account account = accountRepo.findByEmail(email);

		Person person = account.getPerson();

		person.setPersonForms(getDefaultForms());
	}
	
	public Set<Form> getDefaultForms(){
		Set<Form> forms = new HashSet<Form>();

		forms.add(formRepo.findById(1).get()); // Formulario de enfermedades base y condiciones
		forms.add(formRepo.findById(2).get()); // Formulario de Sintomas
		forms.add(formRepo.findById(3).get()); // Formulario referente a COVID19

		return forms;
	}

}
