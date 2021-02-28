package com.core.covid19.controllers;

import java.util.List;
import java.util.Optional;

import com.core.covid19.authentication.util.JwtUtil;
import com.core.covid19.models.requests.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.core.covid19.models.entities.Account;
import com.core.covid19.models.requests.AccountRequest;
import com.core.covid19.services.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

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

	@PostMapping(value = "/doctors/change-password")
	public void changePassword(@RequestHeader("Authorization") String authorization,
								  @RequestBody ChangePasswordRequest data) throws Exception {

		accountService.changePassword(data, jwtUtil.getEmailFromJwtToken(authorization));
	}

}