package com.core.covid19.services;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.covid19.models.entities.Account;
import com.core.covid19.models.entities.Answer;
import com.core.covid19.models.entities.Form;
import com.core.covid19.models.entities.Item;
import com.core.covid19.models.entities.ItemsAnswer;
import com.core.covid19.models.entities.Person;
import com.core.covid19.models.responses.AnswerItemResponse;
import com.core.covid19.models.responses.FormItemResponse;
import com.core.covid19.models.responses.PersonAnswersResponse;
import com.core.covid19.models.responses.PersonFormsResponse;
import com.core.covid19.repos.AccountRepo;
import com.core.covid19.repos.FormRepo;
import com.core.covid19.repos.PersonRepo;

@Service
public class FormService {

	@Autowired
	private FormRepo formRepo;
	
	@Autowired
	private AccountRepo accountRepo;
	
	@Autowired
	private PersonRepo personRepo;

	private List<FormItemResponse> orderedForms(List<Form> forms){
		List<FormItemResponse> formList = new ArrayList<FormItemResponse>();

		for (Form f : forms) {
			List<Item> itemList = new ArrayList<Item>();
			for(Item i : f.getItemsForm())
				itemList.add(i);
			FormItemResponse formItemResponse = new FormItemResponse(f.getId(), f.getTitle(), f.getSubtitle(),
					f.getDescription(), f.getOrderLevel(), itemList.stream().sorted().collect(Collectors.toList()));
			formList.add(formItemResponse);
		}

		formList = formList.stream().sorted().collect(Collectors.toList());

		return formList;
	}

	public PersonFormsResponse findAll() {
		List<Form> forms = formRepo.findAll();

		return new PersonFormsResponse(orderedForms(forms));
	}

	public PersonFormsResponse findAllByPersonEmail(String email) {
		Account account = accountRepo.findByEmail(email);

		if(account.getPerson() == null) return null;

		Person person = account.getPerson();

		return new PersonFormsResponse(orderedForms(new ArrayList<>(person.getPersonForms())));
	}

	public PersonFormsResponse findAllById(Integer id) {
		Person person = personRepo.findById(id).orElseGet(null);

		if(person == null) return null;

		return new PersonFormsResponse(orderedForms(new ArrayList<>(person.getPersonForms())));
	}

	public void addDefaultFormsToPerson(String email) {
		Account account = accountRepo.findByEmail(email);

		if(account.getPerson() == null) return;

		Person person = account.getPerson();

		if(person.getPersonForms().isEmpty())
			for(Form form : getDefaultForms()) person.addForm(form);

		person.setPersonForms(getDefaultForms());
	}
	
	public Set<Form> getDefaultForms(){
		Set<Form> forms = new HashSet<Form>();

		forms.add(formRepo.findById(1).get()); // Formulario de enfermedades base y condiciones
		forms.add(formRepo.findById(2).get()); // Formulario de Sintomas
		forms.add(formRepo.findById(3).get()); // Formulario referente a COVID19

		return forms;
	}
	
	public PersonAnswersResponse getAnswersForm(int idPerson, int idForm) {

		Person person = personRepo.findById(idPerson).orElseGet(null);

		Set<Answer> answers = person.getPersonAnswers();
		
		List<AnswerItemResponse> answersItemsResponse = new ArrayList();

        for (Answer a : answers) {
        	int id = a.getForm().getId();
	        if (id == idForm) {
	        	List<ItemsAnswer> itemsAnswerList = new ArrayList();
	        	for(ItemsAnswer ia : a.getAnswers())
	        		itemsAnswerList.add(ia);
	        	AnswerItemResponse answerItemResponse = new AnswerItemResponse(a.getId(), 
	        			a.getForm(), a.getAnswerDate(), itemsAnswerList);
				answersItemsResponse.add(answerItemResponse);
        	}
        }
		answersItemsResponse = answersItemsResponse.stream().sorted().collect(Collectors.toList());
		return new PersonAnswersResponse(answersItemsResponse);
	}

}
