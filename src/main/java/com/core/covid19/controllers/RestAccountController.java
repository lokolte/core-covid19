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

import com.core.covid19.models.Account;
import com.core.covid19.repos.AccountRepo;

@RestController
@RequestMapping("/account")
public class RestAccountController {

	@Autowired
	private AccountRepo accountRepo;
	
	@GetMapping
	public List<Account> list(){
		return accountRepo.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public Optional<Account> get(@PathVariable("id") Integer id){
		return accountRepo.findById(id);
	}

	@PostMapping
	public void insert(@RequestBody Account account){
		accountRepo.save(account);
	}
	
	@PutMapping
	public void modify(@RequestBody Account account){
		accountRepo.save(account);
	}
	
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Integer id) {
		accountRepo.deleteById(id);
	}

}