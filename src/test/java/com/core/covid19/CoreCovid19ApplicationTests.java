package com.core.covid19;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;

import com.core.covid19.authentication.util.JwtUtil;
import com.core.covid19.models.entities.Account;
import com.core.covid19.models.entities.Status;
import com.core.covid19.models.entities.Location;
import com.core.covid19.models.entities.Person;
import com.core.covid19.models.enums.ClaimsTypes;
import com.core.covid19.models.enums.PersonStatus;
import com.core.covid19.models.enums.Sex;
import com.core.covid19.repos.AccountRepo;
import com.core.covid19.repos.LocationRepo;
import com.core.covid19.repos.PersonRepo;
import com.core.covid19.repos.StatusRepo;
import com.core.covid19.services.CustomUserDetailService;

@SpringBootTest
class CoreCovid19ApplicationTests {
	
	@Autowired
	private AccountRepo accountRepo;
	
	@Autowired
	private PersonRepo personRepo;
	
	@Autowired
	private LocationRepo locationRepo;
	
	@Autowired
	private StatusRepo statusRepo;
	
//	@Autowired
//	private CustomUserDetailService customUserDetailService;
	
//	@Autowired
//	private JwtUtil jwtUtil;
	
	//@Autowired
	//private BCryptPasswordEncoder bcrypt;

//	@Test
//	void createAccountTest() {
//		Account account = accountRepo.findByEmail("jaaguilarmeza@gmail.com");
//		//account.setPassword("password"); //bcrypt.encode("password"));
//		
//		System.out.println("################### user details: " + customUserDetailService.loadUserByUsername(""));
//		
//		Account account2 = accountRepo.save(account);
//		
//		//assert(account.getPassword().equals(account2.getPassword()));
//	}
	/*
	@Test
	void deletePersonTest() {
		
		Location location = new Location();
		
		location.setLatitude(-25.245729);
		location.setLongitude(-57.725436);
				
		Location loc = locationRepo.save(location);
		
		accountRepo.findByEmail("test@gmail.com");
		
		Status status = statusRepo.findByName(PersonStatus.HEALTHY.toString());
		
		Person person = new Person();
		
		person.setLocation(location);
		person.setStatus(status);
		
		person.setDocument("1");
		person.setName("fulano");
		person.setLastname("De Tal");
		person.setPhone("0985123456");
		person.setSex(Sex.MASCULINO.toString());
		
		personRepo.save(person);
		
		Person b = personRepo.findByDocument("1");
		
		//System.out.println("############### get person: " + b.getName() + " " + b.getLastname());
		
		//System.out.println("############### assert igualdad: " + person.getName().equals(b.getName()));
		
		assert(person.getName().equals(b.getName()));
		
		personRepo.delete(person);
		
		Person c = personRepo.findByDocument("1");
		
		//System.out.println("############### assert null: " + (c == null));
		
		assert(c == null);
	}
	*/
//	@Test
//	void verifyRoleTest() {
//		
//		assert(ClaimsTypes.ROLE.toString().equals("ROLE"));
//	}
	
//	@Test
//	void verifyTokenTest() {
//				
//		UserDetails user = customUserDetailService.loadUserByUsername("chuchosoft.239@gmail.com");
//		
//		String token = jwtUtil.generateToken(user);
//		
//		System.out.println("########### token: " + token);
//		
//		assert(jwtUtil.validateToken(token, user));
//		
//		assert(!token.equals(""));
//	}

}
