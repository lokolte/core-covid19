package com.core.covid19;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.core.covid19.models.Account;
import com.core.covid19.models.enums.ClaimsTypes;
import com.core.covid19.repos.AccountRepo;

@SpringBootTest
class CoreCovid19ApplicationTests {
	
	@Autowired
	private AccountRepo accauntRepo;
	
	//@Autowired
	//private BCryptPasswordEncoder bcrypt;

//	@Test
//	void contextLoads() {
//		Account account = accauntRepo.findByEmail("jaaguilarmeza@gmail.com");
//		account.setPassword(bcrypt.encode("password"));
//		
//		Account account2 = accauntRepo.save(account);
//		
//		assert(account.getPassword().equals(account2.getPassword()));
//	}
	
	@Test
	void contextLoads() {
		
		assert(ClaimsTypes.ROLE.toString().equals("ROLE"));
	}

}
