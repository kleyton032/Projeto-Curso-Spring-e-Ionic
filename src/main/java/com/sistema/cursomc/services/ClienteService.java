package com.sistema.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sistema.cursomc.dto.ClienteDTO;
import com.sistema.cursomc.dto.ClienteNewDTO;
import com.sistema.cursomc.model.Cidade;
import com.sistema.cursomc.model.Cliente;
import com.sistema.cursomc.model.Endereco;
import com.sistema.cursomc.model.enums.TipoCliente;
import com.sistema.cursomc.repositories.ClienteRepository;
import com.sistema.cursomc.repositories.EnderecoRepository;
import com.sistema.cursomc.services.exceptions.DataIntegrityException;
import com.sistema.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public Cliente find(Integer id) {
		Optional<Cliente> objeto = clienteRepository.findById(id);
		return objeto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));

	}
	
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente = clienteRepository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
		
	}
	
	public Cliente update(Cliente cliente) {
		Cliente newCliente= find(cliente.getId());
		updateData(newCliente, cliente); 
		return clienteRepository.save(newCliente);
	}
	
	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}
	

	public void delete(Integer id) {
		find(id);
		try {
		clienteRepository.deleteById(id);
		
		} catch (DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir o cliente porque há pedidos associados");
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
		return new Cliente(clienteDto.getId(), clienteDto.getNome(), null, clienteDto.getEmail(), null, null);
	}
	
	//sobrecarga do método acima para nova classe dto de cliente para inserção de um novo cliente
	public Cliente fromDto(ClienteNewDTO clienteNewDto) {
		Cliente cliente = new Cliente(null, clienteNewDto.getNome(), clienteNewDto.getCpfOuCnpj(), clienteNewDto.getEmail(), TipoCliente.toEnum(clienteNewDto.getTipoCliente()), passwordEncoder.encode(clienteNewDto.getSenha()));
		Cidade cidade = new Cidade(clienteNewDto.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, clienteNewDto.getLogradouro(), clienteNewDto.getNumero(), clienteNewDto.getComplemento(), clienteNewDto.getBairro(), clienteNewDto.getCep(), cliente, cidade);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(clienteNewDto.getTelefone1());
		
		if(clienteNewDto.getTelefone2() != null) {
			cliente.getTelefones().add(clienteNewDto.getTelefone2());
		}
		if(clienteNewDto.getTelefone3() != null) {
			cliente.getTelefones().add(clienteNewDto.getTelefone3());
	}
	
		return cliente;
	
	}
}
