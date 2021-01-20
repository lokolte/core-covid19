package com.core.covid19.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

	public PersonFormsResponse findAllByPersonEmail(String email) {
		Account account = accountRepo.findByEmail(email);

		if(account.getPerson() == null) return null;

		Person person = account.getPerson();

		List<FormItemResponse> formList = new ArrayList<FormItemResponse>();

        for (Form f : person.getPersonForms()) {
        	List<Item> itemList = new ArrayList<Item>();
        	for(Item i : f.getItemsForm())
        		itemList.add(i);
        	FormItemResponse formItemResponse = new FormItemResponse(f.getId(), f.getTitle(), f.getSubtitle(), f.getOrderLevel(), itemList.stream().sorted().collect(Collectors.toList()));
        	formList.add(formItemResponse);
        }

        formList = formList.stream().sorted().collect(Collectors.toList());

		return new PersonFormsResponse(formList);
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

		Optional<Person> p = personRepo.findById(idPerson);
		if (p.isEmpty()) return null;
		Person person = p.get();

		List<AnswerItemResponse> answers = new ArrayList<AnswerItemResponse>();

        for (Answer a : person.getPersonAnswers()) {
        	int id = a.getForm().getId();
	        if (id == idForm) {
	        	List<ItemsAnswer> itemsAnswerList = new ArrayList<ItemsAnswer>();
	        	for(ItemsAnswer ia : a.getAnswers())
	        		itemsAnswerList.add(ia);
	        	AnswerItemResponse answerItemResponse = new AnswerItemResponse(a.getId(), 
	        			a.getForm(), a.getAnswerDate(), itemsAnswerList);
	        	answers.add(answerItemResponse);
        	}
        }
        answers = answers.stream().sorted().collect(Collectors.toList());
		return new PersonAnswersResponse(answers);
	}

}
