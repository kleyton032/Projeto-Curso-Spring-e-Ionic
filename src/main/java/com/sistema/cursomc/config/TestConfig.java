package com.sistema.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.sistema.cursomc.services.DataBaseService;
import com.sistema.cursomc.services.EmailService;
import com.sistema.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DataBaseService dataBaseService;
	
	@Bean
	public boolean instanciaDataBase() throws ParseException {
		dataBaseService.instanciaTestDataBase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
	
}
