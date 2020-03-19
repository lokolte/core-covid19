package com.core.covid19;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;

import com.core.covid19.authentication.util.JwtUtil;
import com.core.covid19.models.entities.Account;
import com.core.covid19.models.enums.ClaimsTypes;
import com.core.covid19.repos.AccountRepo;
import com.core.covid19.services.CustomUserDetailService;

@SpringBootTest
class CoreCovid19ApplicationTests {
	
	@Autowired
	private AccountRepo accauntRepo;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	//@Autowired
	//private BCryptPasswordEncoder bcrypt;

//	@Test
//	void createAccountTest() {
//		Account account = accauntRepo.findByEmail("jaaguilarmeza@gmail.com");
//		account.setPassword("password"); //bcrypt.encode("password"));
//		
//		Account account2 = accauntRepo.save(account);
//		
//		assert(account.getPassword().equals(account2.getPassword()));
//	}
	
	@Test
	void verifyRoleTest() {
		
		assert(ClaimsTypes.ROLE.toString().equals("ROLE"));
	}
	
	@Test
	void verifyTokenTest() {
				
		UserDetails user = customUserDetailService.loadUserByUsername("chuchosoft.239@gmail.com");
		
		String token = jwtUtil.generateToken(user);
		
		System.out.println("########### token: " + token);
		
		assert(jwtUtil.validateToken(token, user));
		
		assert(!token.equals(""));
	}

}
