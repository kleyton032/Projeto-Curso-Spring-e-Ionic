package com.sistema.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.sistema.cursomc.services.DataBaseService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DataBaseService dataBaseService;
	
	//controlando a instanciação da base de dados
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instanciaDataBase() throws ParseException {
		
		//controlando a instanciação da base de dados
		if(!"create".equals(strategy)) {
			return false;
		}
		dataBaseService.instanciaTestDataBase();
		return true;
	}
	
	
}
