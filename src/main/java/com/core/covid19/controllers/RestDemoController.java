package com.core.covid19.controllers;

import java.util.List;

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
@RequestMapping("/personas")
public class RestDemoController {

	@Autowired
	private AccountRepo accountRepo;
	
	@GetMapping
	public List<Account> listar(){
		return accountRepo.findAll();
	}

	@PostMapping
	public void insertar(@RequestBody Account account){
		accountRepo.save(account);
	}
	
	@PutMapping
	public void modificar(@RequestBody Account account){
		accountRepo.save(account);
	}
	
	@DeleteMapping(value = "/{id}")
	public void eliminar(@PathVariable("id") Integer id) {
		accountRepo.deleteById(id);
	}

}