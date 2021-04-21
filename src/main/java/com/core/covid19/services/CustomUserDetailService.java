package com.core.covid19.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.core.covid19.models.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.core.covid19.models.entities.Account;
import com.core.covid19.models.enums.ClaimsTypes;
import com.core.covid19.repos.AccountRepo;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private AccountRepo accountRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account = accountRepo.findByEmail(email);

		if(account == null) return null;

		List<GrantedAuthority> roles = new ArrayList<>();
		if (account.getRoles() != null) {
			for (Role r : account.getRoles()) {
				roles.add(new SimpleGrantedAuthority(r.getName()));
			}
		}
		UserDetails userDet = new User(account.getEmail(), account.getPassword(), roles);

		return userDet;
	}
	
	public Map<String, Object> createClaims(UserDetails userDetails){
		Map<String, Object> claims = new HashMap<>();

		Account account = accountRepo.findByEmail(userDetails.getUsername());

		if (account.getRoles() != null) {
			for (Role r : account.getRoles()) {
				claims.put(ClaimsTypes.ROLE.toString(), (Object) r.getName());
			}
		}
		return claims;
	}
	
}
