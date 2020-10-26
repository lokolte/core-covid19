package com.core.covid19.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.covid19.authentication.util.JwtUtil;
import com.core.covid19.models.entities.Hospital;
import com.core.covid19.models.entities.Person;
import com.core.covid19.models.requests.PersonRequest;
import com.core.covid19.services.HospitalService;
import com.core.covid19.services.PersonService;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping
	public List<Hospital> get(@RequestHeader("Authorization") String authorization){
		return hospitalService.getTenCloser(jwtUtil.getEmailFromJwtToken(authorization));
	}
}