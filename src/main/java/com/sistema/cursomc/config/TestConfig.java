package com.sistema.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.sistema.cursomc.services.DataBaseService;

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
	
	
}
