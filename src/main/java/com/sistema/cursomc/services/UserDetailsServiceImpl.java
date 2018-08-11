package com.sistema.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sistema.cursomc.model.Cliente;
import com.sistema.cursomc.repositories.ClienteRepository;
import com.sistema.cursomc.security.UserSpringSecurity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			 throw new UsernameNotFoundException(email);
		}
		return new UserSpringSecurity(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
	}

}
