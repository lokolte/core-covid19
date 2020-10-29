package com.core.covid19.models.entities;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

import java.sql.Timestamp;


/**
 * The persistent class for the contact database table.
 * 
 */
@Entity
@Table(name="message")
@Data
@NamedQuery(name="Message.findAll", query="SELECT m FROM Message m")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=true, length=1500)
	private String messageText;

	@Column(name="send_date", nullable=false)
	private Timestamp sendDate;

	@Column(name="person_sender_id", nullable=false)
	private Integer personSenderId;

	@Column(name="person_received_id")
	private Integer personReceivedId;

	public Message() {
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

	public Timestamp getSendDate() {
		return sendDate;
	}

	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
	}

	public Integer getPersonSenderId() {
		return personSenderId;
	}

	public void setPersonSenderId(Integer personSenderId) {
		this.personSenderId = personSenderId;
	}

	public Integer getPersonReceivedId() {
		return personReceivedId;
	}

	public void setPersonReceivedId(Integer personReceivedId) {
		this.personReceivedId = personReceivedId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}