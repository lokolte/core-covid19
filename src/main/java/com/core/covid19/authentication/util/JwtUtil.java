package com.core.covid19.authentication.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.core.covid19.services.CustomUserDetailService;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

	@Autowired
	CustomUserDetailService customUserDetailService;

	// nick name with not to strong password 64
	private String SECRET_KEY = "bG9rb2x0ZTpsMGswbHRlLg==";

	public String extractEmail(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

//    private Boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = customUserDetailService.createClaims(userDetails);
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				// .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				// one year duration of the jwt token
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String email = extractEmail(token);
		return (email.equals(userDetails.getUsername())); // && !isTokenExpired(token));
	}

	public String getEmailFromJwtToken(String authorizationHeader) {
		return extractEmail(getJwtTokenFromHeader(authorizationHeader));
	}
	
	public String getJwtTokenFromHeader(String authorizationHeader) {
		
		String jwt = null;
		
		if (isValidAuthorizationHeader(authorizationHeader))
			jwt = authorizationHeader.substring(7);
				
		return jwt;
		
	}
	
	public Boolean isValidAuthorizationHeader(String authorizationHeader) {
		return authorizationHeader != null && authorizationHeader.startsWith("Bearer ");
	}

}
