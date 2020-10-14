package com.core.covid19.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.covid19.authentication.util.JwtUtil;
import com.core.covid19.models.entities.Form;
import com.core.covid19.services.FormService;

@RestController
@RequestMapping("/forms")
public class FormController {

	@Autowired
	private FormService formService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping(value="")
	public Set<Form> listAll(@RequestHeader("Authorization") String authorization) {
		return formService.findAllByPersonEmail(jwtUtil.getEmailFromJwtToken(authorization));
	}

	@PostMapping(value = "/{id}")
	public void asign(@RequestHeader("Authorization") String authorization) {
		formService.addDefaultFormsToPerson(jwtUtil.getEmailFromJwtToken(authorization));
	}

}
