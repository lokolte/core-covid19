package com.core.covid19.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.core.covid19.models.responses.PersonResponse;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping("/doctors")
	public List<PersonResponse> getDoctors() {
		return accountService.getDoctors();
	}

	@PostMapping(value="/doctors/import")
	public void cargar(@RequestParam("file") MultipartFile file) throws IOException, InvalidFormatException {

		System.err.println("Cargar datos !!!");
		accountService.loadData(file);
	}

}