package com.sistema.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.cursomc.model.Cliente;
import com.sistema.cursomc.repositories.ClienteRepository;
import com.sistema.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	
	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> objeto = clienteRepository.findById(id);
		return objeto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));

	}
	
}
