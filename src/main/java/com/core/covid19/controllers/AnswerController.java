package com.core.covid19.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.covid19.authentication.util.JwtUtil;
import com.core.covid19.models.entities.Answer;
import com.core.covid19.models.responses.PersonAnswersResponse;
import com.core.covid19.services.AnswerService;

@RestController
@RequestMapping("/answers")
public class AnswerController {
	@Autowired
	private AnswerService answerService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping
	public PersonAnswersResponse listAll(@RequestHeader("Authorization") String authorization) {
		return answerService.findAllByPersonEmail(jwtUtil.getEmailFromJwtToken(authorization));
	}

	@PostMapping
	public Answer createAnswer(@RequestHeader("Authorization") String authorization, @RequestBody Answer answer) {
		return answerService.addAnswersToPerson(jwtUtil.getEmailFromJwtToken(authorization), answer);
	}

}
