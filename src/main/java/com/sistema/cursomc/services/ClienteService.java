package com.sistema.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.sistema.cursomc.dto.ClienteDTO;
import com.sistema.cursomc.model.Cliente;
import com.sistema.cursomc.repositories.ClienteRepository;
import com.sistema.cursomc.services.exceptions.DataIntegrityException;
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
	
	public Cliente update(Cliente cliente) {
		Cliente newCliente= find(cliente.getId());
		updateData(newCliente, cliente); 
		return clienteRepository.save(newCliente);
	}

	public void delete(Integer id) {
		find(id);
		try {
		clienteRepository.deleteById(id);
		
		} catch (DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir categoria com produtos associados");
		}
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPages, String ordeBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPages, Direction.valueOf(direction), ordeBy);
		return clienteRepository.findAll(pageRequest);
	}
	//método transformar um cliente DTO em objeto
	public Cliente fromDto(ClienteDTO clienteDto) {
		return new Cliente(clienteDto.getId(), clienteDto.getNome(), clienteDto.getEmail(), null, null);
	}
	
	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}
	
}
