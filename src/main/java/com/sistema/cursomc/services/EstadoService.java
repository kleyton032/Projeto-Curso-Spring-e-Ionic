package com.sistema.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.cursomc.model.Estado;
import com.sistema.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repository;

	//método para retornar todos os estados ordenandos por nome
	public List<Estado> findAll(){
		return repository.findAllByOrderByNome();
	}
	
}
