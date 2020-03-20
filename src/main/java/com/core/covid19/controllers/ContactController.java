package com.core.covid19.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.covid19.authentication.util.JwtUtil;
import com.core.covid19.models.requests.ContactRequest;
import com.core.covid19.models.responses.ContactResponse;
import com.core.covid19.services.ContactService;

@RestController
@RequestMapping("/contacts")
public class ContactController {

	@Autowired
	private ContactService contactService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping()
	public List<ContactResponse> listAll(@RequestHeader("Authorization") String authorization) {
		return contactService.findAllByDocument(jwtUtil.getEmailFromJwtToken(authorization));
	}

	@PostMapping(value = "/{document}")
	public ContactResponse insert(@PathVariable("document") String document,
			@RequestHeader("Authorization") String authorization, @RequestBody ContactRequest contactRequest) {
		return contactService.insert(document, jwtUtil.getEmailFromJwtToken(authorization), contactRequest);
	}

}
