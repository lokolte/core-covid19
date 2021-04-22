package com.core.covid19.controllers.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core.covid19.authentication.util.JwtUtil;
import com.core.covid19.models.entities.Account;
import com.core.covid19.models.requests.AuthenticationRequest;
import com.core.covid19.models.responses.AuthenticationResponse;
import com.core.covid19.repos.AccountRepo;
import com.core.covid19.services.CustomUserDetailService;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private AccountRepo accountRepo;

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@RequestMapping(value = "/authenticate", method=RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		Account account = accountRepo.findByEmail(authenticationRequest.getEmail());

		if (!account.isVerify()) {
			throw new Exception("Usuario no verificado. Verifique su correo electronico.");
		}

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
			);
		} catch (BadCredentialsException e) {
			throw new Exception("Usuario o contraseña incorrectos.", e);
		}

		final UserDetails userDetails = customUserDetailService
				.loadUserByUsername(authenticationRequest.getEmail());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt, account));
	}
}
