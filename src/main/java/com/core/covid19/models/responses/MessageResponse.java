package com.core.covid19.models.responses;

import java.io.Serializable;
import java.util.List;

import com.core.covid19.models.entities.Person;

public class MessageResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<MessageItem> messages;
	
	private Person myData;

	public MessageResponse(List<MessageItem> messages, Person myData) {
		this.messages = messages;
		this.myData = myData;
	}

	public List<MessageItem> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageItem> messages) {
		this.messages = messages;
	}

	public Person getMyData() {
		return myData;
	}

	public void setMyData(Person myData) {
		this.myData = myData;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}