package com.core.covid19.controllers;

import com.core.covid19.models.entities.Hospital;
import com.core.covid19.models.entities.Person;
import com.core.covid19.models.requests.DoctorRequest;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.core.covid19.authentication.util.JwtUtil;
import com.core.covid19.models.responses.HospitalsResponse;
import com.core.covid19.services.HospitalService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping
	public List<Hospital> getAll(@RequestParam(value = "idDoctor", required = false) Integer idDoctor) {
		if (idDoctor != null)
			return hospitalService.getHospitalsByDoctor(idDoctor);
		return hospitalService.getAll();
	}

	@GetMapping("/{id}")
	public Hospital get(@PathVariable("id") Integer id) {
		return hospitalService.get(id);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		hospitalService.delete(id);
	}

	@PostMapping
	public void save(@RequestBody Hospital data) {
		hospitalService.save(data);
	}

	@GetMapping(value="/my")
	public HospitalsResponse get(@RequestHeader("Authorization") String authorization){
		return hospitalService.getTenCloser(jwtUtil.getEmailFromJwtToken(authorization));
	}

	@PostMapping(value="/cargar")
	public void cargar(@RequestParam("file") MultipartFile file) throws IOException, InvalidFormatException {

		hospitalService.cargar(file);
	}
}