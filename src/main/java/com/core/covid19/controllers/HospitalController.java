package com.core.covid19.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.covid19.authentication.util.JwtUtil;
import com.core.covid19.models.responses.HospitalsResponse;
import com.core.covid19.services.HospitalService;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping
	public HospitalsResponse get(@RequestHeader("Authorization") String authorization){
		return hospitalService.getTenCloser(jwtUtil.getEmailFromJwtToken(authorization));
	}
}