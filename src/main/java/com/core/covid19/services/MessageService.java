package com.core.covid19.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.covid19.models.entities.Account;
import com.core.covid19.models.entities.Message;
import com.core.covid19.models.entities.Person;
import com.core.covid19.models.responses.MessageItem;
import com.core.covid19.models.responses.MessageResponse;
import com.core.covid19.repos.AccountRepo;
import com.core.covid19.repos.MessageRepo;
import com.core.covid19.repos.PersonRepo;

@Service
public class MessageService {

	@Autowired
	private MessageRepo messageRepo;

	@Autowired
	private PersonRepo personRepo;

	@Autowired
	private AccountRepo accountRepo;

	public MessageResponse findAllMyMessageByEmail(String email) {
		Account account = accountRepo.findByEmail(email);

		if(account.getPerson() == null) return null;

		Person person = account.getPerson();

		List<Message> receiverMessages = messageRepo.findByPersonReceivedId(person.getId());
		List<Message> senderMessages = messageRepo.findByPersonSenderId(person.getId());

		List<MessageItem> messages = new ArrayList<MessageItem>();

		for(Message message : receiverMessages)
			messages.add(new MessageItem(message.getId(), message.getMessageText(), message.getSendDate(), personRepo.findById(message.getPersonSenderId()).get(), false));

		for(Message message : senderMessages)
			messages.add(new MessageItem(message.getId(), message.getMessageText(), message.getSendDate(), personRepo.findById(message.getPersonSenderId()).get(), true));

		messages = messages.stream().sorted().collect(Collectors.toList());
	
		return new MessageResponse(messages, person);
	}

	public Message insert(String email, Message message) {
		
		Message messageToSave = new Message();
		messageToSave.setMessageText(message.getMessageText());
		messageToSave.setSendDate(message.getSendDate());
		if(message.getPersonReceivedId() != 0) messageToSave.setPersonReceivedId(message.getPersonReceivedId());
		messageToSave.setPersonSenderId(message.getPersonSenderId());
		
		return messageRepo.save(messageToSave);
	}

}
