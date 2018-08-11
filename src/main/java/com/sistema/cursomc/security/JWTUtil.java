package com.sistema.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.experation}")
	private Long experation;
	
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setExpiration(new Date(System.currentTimeMillis() + experation))
				//qual algoritimo de geração do token 
				//passando em conjunto com minha string definida no properties
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
}
