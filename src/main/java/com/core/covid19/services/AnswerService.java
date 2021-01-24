package com.core.covid19.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.covid19.models.entities.Account;
import com.core.covid19.models.entities.Answer;
import com.core.covid19.models.entities.ItemsAnswer;
import com.core.covid19.models.entities.Person;
import com.core.covid19.models.responses.AnswerItemResponse;
import com.core.covid19.models.responses.PersonAnswersResponse;
import com.core.covid19.repos.AccountRepo;
import com.core.covid19.repos.AnswerRepo;
import com.core.covid19.repos.ItemsAnswerRepo;
import com.core.covid19.repos.PersonRepo;

@Service
public class AnswerService {

	@Autowired
	private AnswerRepo answerRepo;

	@Autowired
	private AccountRepo accountRepo;

	@Autowired
	private ItemsAnswerRepo itemsAnswerRepo;

	@Autowired
	private PersonRepo personRepo;

	public PersonAnswersResponse findAllByPersonEmail(String email) {
		Account account = accountRepo.findByEmail(email);

		if(account.getPerson() == null) return null;

		Person person = account.getPerson();

		List<AnswerItemResponse> answers = new ArrayList<AnswerItemResponse>();

        for (Answer a : person.getPersonAnswers()) {
        	List<ItemsAnswer> itemsAnswerList = new ArrayList<ItemsAnswer>();
        	for(ItemsAnswer ia : a.getAnswers())
        		itemsAnswerList.add(ia);
        	AnswerItemResponse answerItemResponse = new AnswerItemResponse(a.getId(), a.getForm(), a.getAnswerDate(), itemsAnswerList);
        	answers.add(answerItemResponse);
        }

        answers = answers.stream().sorted().collect(Collectors.toList());

		return new PersonAnswersResponse(answers);
	}

	public PersonAnswersResponse findAllByPersonId(Integer id) {
		Account account = accountRepo.getAccountByPersonId(id);

		if(account.getPerson() == null) return null;

		Person person = account.getPerson();

		List<AnswerItemResponse> answers = new ArrayList<AnswerItemResponse>();

		for (Answer a : person.getPersonAnswers()) {
			List<ItemsAnswer> itemsAnswerList = new ArrayList<ItemsAnswer>();
			for(ItemsAnswer ia : a.getAnswers())
				itemsAnswerList.add(ia);
			AnswerItemResponse answerItemResponse = new AnswerItemResponse(a.getId(), a.getForm(), a.getAnswerDate(), itemsAnswerList);
			answers.add(answerItemResponse);
		}

		answers = answers.stream().sorted().collect(Collectors.toList());

		return new PersonAnswersResponse(answers);
	}

	public Answer addAnswersToPerson(String email, Answer answer){
		Account account = accountRepo.findByEmail(email);

		Person person = account.getPerson();

		Answer answerSaved = answerRepo.save(answer);

		Set<ItemsAnswer> itemsAnswerList = new HashSet<ItemsAnswer>();

		for(ItemsAnswer ia : answer.getAnswers()) {
			ia.setAnswer(answerSaved);
			itemsAnswerList.add(itemsAnswerRepo.save(ia));
		}

		answer.setAnswers(itemsAnswerList);

		answerRepo.save(answer);

		person.getPersonAnswers().add(answerSaved);

		personRepo.save(person);

		return answerSaved;
	}

}