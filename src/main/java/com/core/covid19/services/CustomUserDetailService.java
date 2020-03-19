package com.core.covid19.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private AccountRepo accountRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Account account = accountRepo.findByEmail(email);
						
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(account.getRole().getName()));
		
		UserDetails userDet = new User(account.getEmail(), account.getPassword(), roles);
		
		return userDet;
	}
	
	public Map<String, Object> createClaims(UserDetails userDetails){
		Map<String, Object> claims = new HashMap<>();
		
		Account account = accountRepo.findByEmail(userDetails.getUsername());
		
		claims.put(ClaimsTypes.ROLE.toString(), (Object)account.getRole().getName());
		
		return claims;
	}
	
}