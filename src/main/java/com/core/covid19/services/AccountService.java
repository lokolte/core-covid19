package com.core.covid19.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.covid19.models.entities.Role;
import com.core.covid19.models.enums.Roles;
import com.core.covid19.models.entities.Account;
import com.core.covid19.models.requests.AccountRequest;
import com.core.covid19.repos.AccountRepo;
import com.core.covid19.repos.RoleRepo;

@Service
public class AccountService {	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private AccountRepo accountRepo;
	
	public Account insert(AccountRequest accountRequest) {
		Role role = null;
		
		if(accountRequest.getRole() != null)
			role = roleRepo.findByName(accountRequest.getRole().getName());
		else role = roleRepo.findByName(Roles.CIVIL.toString());
		
		Account account = new Account();
		account.setEmail(accountRequest.getEmail());
		account.setPassword(accountRequest.getPassword());
		account.setRole(role);
		
		return accountRepo.save(account);
	}
	
	public List<Account> findAll(){
		return accountRepo.findAll();
	}
	
	public Optional<Account> findById(Integer id) {
		return accountRepo.findById(id);
	}
	
	public Account modify(AccountRequest accountRequest){
		Role role = roleRepo.findByName(accountRequest.getRole().getName());
		
		Account account = new Account();
		account.setEmail(accountRequest.getEmail());
		account.setPassword(accountRequest.getPassword());
		account.setRole(role);
		
		return accountRepo.save(account);
	}
	
	public void deleteById(String email) {
		accountRepo.delete(accountRepo.findByEmail(email));
	}
}