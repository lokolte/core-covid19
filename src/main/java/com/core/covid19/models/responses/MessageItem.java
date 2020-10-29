package com.core.covid19.models.responses;

import java.io.Serializable;
import java.sql.Timestamp;

import com.core.covid19.models.entities.Person;

public class MessageItem implements Serializable, Comparable<MessageItem> {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String messageText;

	private Timestamp messageDate;

	private Person person;

	private Boolean receiver;

	public MessageItem(Integer id, String messageText, Timestamp messageDate, Person person, Boolean receiver) {
		this.id = id;
		this.messageText = messageText;
		this.messageDate = messageDate;
		this.person = person;
		this.receiver = receiver;
	}

	@Override
	public int compareTo(MessageItem message) {
		return this.getMessageDate().compareTo(message.getMessageDate());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public Timestamp getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Timestamp messageDate) {
		this.messageDate = messageDate;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Boolean getReceiver() {
		return receiver;
	}

	public void setReceiver(Boolean receiver) {
		this.receiver = receiver;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}