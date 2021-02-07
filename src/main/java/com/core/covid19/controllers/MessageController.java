package com.core.covid19.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.covid19.authentication.util.JwtUtil;
import com.core.covid19.models.entities.Message;
import com.core.covid19.models.responses.MessageResponse;
import com.core.covid19.services.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping()
	public MessageResponse listAll(@RequestHeader("Authorization") String authorization) {
		return messageService.findAllMyMessageByEmail(jwtUtil.getEmailFromJwtToken(authorization));
	}

	@PostMapping
	public Message insert(@RequestBody Message message) {
		return messageService.insert(message);
	}

}
