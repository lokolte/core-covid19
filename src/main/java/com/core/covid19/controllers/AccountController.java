package com.core.covid19.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.core.covid19.authentication.util.JwtUtil;
import com.core.covid19.models.entities.Hospital;
import com.core.covid19.models.entities.Person;
import com.core.covid19.models.requests.ChangePasswordRequest;
import com.core.covid19.models.requests.DoctorRequest;
import com.core.covid19.models.requests.DoctorResponse;
import com.core.covid19.models.responses.PersonResponse;
import com.core.covid19.services.PersonService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.core.covid19.models.entities.Account;
import com.core.covid19.models.requests.AccountRequest;
import com.core.covid19.services.AccountService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private PersonService personService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping
	public List<Account> list(){
		return accountService.findAll();
	}

	@GetMapping(value = "/{id}")
	public Optional<Account> get(@PathVariable("id") Integer id){
		return accountService.findById(id);
	}

	@PostMapping(value = "/signup")
	public Account insert(@RequestBody AccountRequest accountRequest){
		return accountService.insert(accountRequest);
	}

	@PutMapping
	public Account modify(@RequestBody AccountRequest accountRequest){
		return accountService.modify(accountRequest);
	}

	@DeleteMapping(value = "/{email}")
	public void delete(@PathVariable("email") String email) {
		accountService.deleteById(email);
	}

	@PostMapping("/doctors/new")
	public void insert(@RequestBody DoctorRequest data) throws Exception {
		accountService.insertDoctor(data);
	}

	@DeleteMapping("/person/{id}")
	public void delete(@PathVariable("id") Integer id) {
		accountService.delete(id);
	}

	@GetMapping("/doctors")
	public List<PersonResponse> getDoctors() {
		return accountService.getDoctors();
	}

	@GetMapping("/doctors/{id}/hospitals")
	public List<Hospital> getHospitalsDoctor(@PathVariable("id") Integer id) {
		return accountService.getHospitalsDoctor(id);
	}

	@PostMapping("/doctors/{id}/hospitals")
	public void saveHospitalsDoctor(@PathVariable("id") Integer id, @RequestBody List<Hospital> hospitals) {
		accountService.saveHospitalsDoctor(id, hospitals);
	}

	@PostMapping(value="/doctors/import")
	public void cargar(@RequestParam("file") MultipartFile file) throws IOException, InvalidFormatException {

		accountService.loadData(file);
	}

	@PostMapping(value = "/doctors/change-password")
	public void changePassword(@RequestHeader("Authorization") String authorization,
							   @RequestBody ChangePasswordRequest data) throws Exception {

		accountService.changePassword(data, jwtUtil.getEmailFromJwtToken(authorization));
	}

	@GetMapping("/doctors/export")
	public ResponseEntity<Resource> export() throws Exception {

		ByteArrayOutputStream salida = accountService.export();
		byte[] res = salida.toByteArray();
		ByteArrayResource resource = new ByteArrayResource(res);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/octet-stream");
		headers.set("Content-Disposition", "attachment; filename=\"Planilla.xlsx\"");
		String contentType = "application/octet-stream";
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "doctores.xlsx" + "\"")
				.body(resource);
	}

	@GetMapping("/doctors/{id}")
	public DoctorResponse getDoctors(@PathVariable("id") Integer id) {
		return accountService.getDoctor(id);
	}

	@PostMapping("/doctors")
	public void update(@RequestBody DoctorRequest data) {
		Person p = new Person(data);
		personService.modify(data.getEmail(), p);
	}

}