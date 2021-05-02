package com.core.covid19.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.covid19.models.entities.Account;

import java.util.List;

public interface AccountRepo extends JpaRepository<Account, Integer>{
	
	Account findByEmail(String email);
	Account getAccountByPersonId(int person);
	List<Account> getAllByRole(int role);
	List<Account> getAllByRoleAndProvince(int role, int province);
}
