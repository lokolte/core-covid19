package com.core.covid19.services;

import java.util.*;
import java.util.stream.Collectors;

import com.core.covid19.models.responses.*;
import com.core.covid19.repos.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.covid19.models.entities.Account;
import com.core.covid19.models.entities.Answer;
import com.core.covid19.models.entities.Form;
import com.core.covid19.models.entities.Item;
import com.core.covid19.models.entities.ItemsAnswer;
import com.core.covid19.models.entities.Person;
import com.core.covid19.repos.AccountRepo;
import com.core.covid19.repos.FormRepo;
import com.core.covid19.repos.PersonRepo;

@Service
public class FormService {

	@Autowired
	private FormRepo formRepo;

	@Autowired
	private ItemRepo itemRepo;
	
	@Autowired
	private AccountRepo accountRepo;
	
	@Autowired
	private PersonRepo personRepo;

	private List<FormItemResponse> orderedForms(List<Form> forms) {

		List<FormItemResponse> formList = new ArrayList<FormItemResponse>();
		for (Form f : forms) {
			List<Item> itemList = new ArrayList<Item>();
			for(Item i : f.getItemsForm())
				itemList.add(i);
			FormItemResponse formItemResponse = new FormItemResponse(f.getId(), f.getTitle(), f.getSubtitle(),
					f.getOrderLevel(), itemList.stream().sorted().collect(Collectors.toList()), f.getDefault());
			formList.add(formItemResponse);
		}
		formList = formList.stream().sorted().collect(Collectors.toList());
		return formList;
	}

	/**
	 * Obtiene la lista de formularios
	 * @return
	 */
	public PersonFormsResponse findAll() {
		List<Form> forms = formRepo.findAll();
		return new PersonFormsResponse(orderedForms(forms));
	}

	public PersonFormsResponse get(int idForm) {
		Form form = formRepo.getOne(idForm);
		List<Form> list = new ArrayList<>();
		list.add(form);
		return new PersonFormsResponse(orderedForms(list));
	}

	public PersonFormsResponse getFormsAvailable(int idPerson) {
		List<Form> forms = formRepo.findAll();
		Person p = personRepo.getOne(idPerson);
		Set<Form> formsPatient = p.getPersonForms();
		List<Integer> ids = formsPatient.stream().map(Form::getId).collect(Collectors.toList());
		List<Form> res = new ArrayList<>();
		for (Form f : forms) {
			if (!ids.contains(f.getId())) {
				res.add(f);
			}
		}
		return new PersonFormsResponse(orderedForms(res));
	}

	/**
	 * Guarda los datos del Formulario
	 * @param email
	 * @param data
	 */
	public void save(String email, FormItemResponse data) {
		Account account = accountRepo.findByEmail(email);
		if (account == null) return;
		Form f = new Form(data);
		formRepo.save(f);

		if (data.getItemsForm() != null) {
			if (f.getItemsForm() != null)
				f.getItemsForm().clear();
			else
				f.setItemsForm(new HashSet<>());
			for (Item i : data.getItemsForm()) {
				Item item = itemRepo.getOne(i.getId());
				if (item.getForms() == null)
					item.setForms(new HashSet<>());
				item.getForms().add(f);
				itemRepo.save(item);
				f.getItemsForm().add(item);
			}
		}
		formRepo.save(f);
	}

	/**
	 * Lista todos los items
	 * @return
	 */
	public List<QuestionResponse> getItems() {
		List<Item> items = itemRepo.findAll();
		List<QuestionResponse> res = new ArrayList<>();
		items.forEach(i -> res.add(new QuestionResponse(i)));
		return res;
	}

	public List<QuestionResponse> getItems(int idForm) {
		List<Item> items = itemRepo.findAll();
		Form f = formRepo.getOne(idForm);
		List<Integer> ids = f.getItemsForm().stream().map(Item::getId).collect(Collectors.toList());
		List<QuestionResponse> res = new ArrayList<>();
		for (Item i : items) {
			if (!ids.contains(i.getId())) {
				res.add(new QuestionResponse(i));
			}
		}
		return res;
	}

	/**
	 * Obtiene los datos de un item
	 * @param id
	 * @return
	 */
	public QuestionResponse getItem(int id) {
		Item item = itemRepo.findById(id).get();
		return new QuestionResponse(item, true);
	}

	/**
	 * Crea un nuevo item
	 * @param email
	 * @param data
	 */
	public void saveItem(String email, QuestionResponse data) {
		Account account = accountRepo.findByEmail(email);
		if (account == null) return;
		Item i = new Item(data);
		itemRepo.save(i);
	}

	/**
	 * Actualiza los datos del item
	 * @param email
	 * @param id
	 * @param data
	 */
	public void modifyItem(String email, int id, QuestionResponse data) {
		Account account = accountRepo.findByEmail(email);
		if (account == null) return;
		Item i = itemRepo.getOne(id);
		i.setTitle(data.getTitle());
		i.setSubtitle(data.getSubtitle());
		i.setOrderLevel(data.getOrderLevel());
		i.setType(data.getType());
		itemRepo.save(i);
	}

	/**
	 * Obtiene los items del formulario
	 * @param id Id del formulario
	 * @return
	 */
	public List<QuestionResponse> getAnswer(int id) {
		Form f = formRepo.getOne(id);
		Set<Item> items = f.getItemsForm();
		List<QuestionResponse> res = new ArrayList<>();
		items.forEach(i -> res.add(new QuestionResponse(i)));
		return res;
	}

	/**
	 * Actualiza los items del formulario
	 * @param id
	 * @param items
	 */
	public void updateItemsForm(int id, List<QuestionResponse> items) {

		Form f = formRepo.getOne(id);
		if (f == null) return;
		Set<Item> newItems = new HashSet<>();
		items.forEach(i -> newItems.add(new Item(i)));
		f.setItemsForm(newItems);
		formRepo.save(f);
	}

	/**
	 * Obtiene los formularios del usuario logueado
	 * @param email
	 * @return
	 */
	public PersonFormsResponse findAllByPersonEmail(String email) {

		Account account = accountRepo.findByEmail(email);
		if(account.getPerson() == null) return null;
		Person person = account.getPerson();
		return new PersonFormsResponse(orderedForms(new ArrayList<>(person.getPersonForms())));
	}

	/**
	 * Obtiene los formularios del usuario
	 * @param id
	 * @return
	 */
	public PersonFormsResponse findAllById(Integer id) {

		Person person = personRepo.findById(id).orElseGet(null);
		if(person == null) return null;
		return new PersonFormsResponse(orderedForms(new ArrayList<>(person.getPersonForms())));
	}

	/**
	 * Asigna los formularios al paciente
	 * @param id		Id del paciente
	 * @param forms 	Lista de formularios a asignar
	 */
	public void asignForms(int id, List<FormItemResponse> forms) {

		Person person = personRepo.getOne(id);
		Set<Form> list = new HashSet<>();
		for (FormItemResponse f : forms) {
			Form form = formRepo.getOne(f.getId());
			list.add(form);
		}
		person.setPersonForms(list);
		personRepo.save(person);
	}

	public void addDefaultFormsToPerson(String email) {

		Account account = accountRepo.findByEmail(email);
		if (account.getPerson() == null) return;
		Person person = account.getPerson();
		if (person.getPersonForms().isEmpty())
			for(Form form : getDefaultForms()) person.addForm(form);
		person.setPersonForms(getDefaultForms());
	}
	
	public Set<Form> getDefaultForms() {
		return formRepo.getDefaultForms();
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
