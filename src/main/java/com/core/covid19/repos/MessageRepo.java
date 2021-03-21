package com.core.covid19.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.covid19.models.entities.Message;

public interface MessageRepo extends JpaRepository<Message, Integer>{

	List<Message> findByPersonSenderId(Integer personSenderId);
	
	List<Message> findByPersonReceivedId(Integer personReceiverId);
}
