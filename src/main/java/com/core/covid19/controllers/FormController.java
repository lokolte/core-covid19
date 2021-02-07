package com.core.covid19.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.core.covid19.models.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.covid19.authentication.util.JwtUtil;
import com.core.covid19.models.entities.ItemsAnswer;
import com.core.covid19.models.responses.AnswerItemResponse;
import com.core.covid19.models.responses.PersonAnswersResponse;
import com.core.covid19.models.responses.PersonFormsResponse;
import com.core.covid19.services.FormService;

@RestController
@RequestMapping("/forms")
public class FormController {
	@Autowired
	private FormService formService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping
	public PersonFormsResponse listAll() {
		return formService.findAll();
	}

	@GetMapping(value="/my")
	public PersonFormsResponse listAllByEmail(@RequestHeader("Authorization") String authorization) {
		return formService.findAllByPersonEmail(jwtUtil.getEmailFromJwtToken(authorization));
	}

	@GetMapping(value = "/{id}")
	public PersonFormsResponse listAllById(@PathVariable("id") Integer id) {
		return formService.findAllById(id);
	}

	@PostMapping
	public void asign(@RequestHeader("Authorization") String authorization) {
		formService.addDefaultFormsToPerson(jwtUtil.getEmailFromJwtToken(authorization));
	}

}
