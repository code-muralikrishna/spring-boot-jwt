package com.starter.springsecurityJWT.configurer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtUtill {

   private String SECRET_KEY = "secret";
   
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
   
	public Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
   
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
   public String generateToken(UserDetails userdetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userdetails.getUsername());
	}
   
	private String createToken(Map<String, Object> claims, String subject) {
	
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
				.signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
	
	public Boolean validateToken(String token, UserDetails userdetails) {
		final String username = extractUsername(token);
		return (username.equals(userdetails.getUsername()) && !isTokenExpired(token));
	}
   
}
