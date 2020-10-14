package com.core.covid19.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.covid19.models.entities.Account;
import com.core.covid19.models.requests.AccountRequest;
import com.core.covid19.services.AccountService;

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
	public void insert(@RequestBody AccountRequest accountRequest){
		accountService.insert(accountRequest);
	}
	
	@PutMapping
	public Account modify(@RequestBody AccountRequest accountRequest){
		return accountService.modify(accountRequest);
	}
	
	@DeleteMapping(value = "/{email}")
	public void delete(@PathVariable("email") String email) {
		accountService.deleteById(email);
	}

}