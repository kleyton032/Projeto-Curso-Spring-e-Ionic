package com.sistema.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.cursomc.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
	
	//retornar todos os estados ordenando por nome
	@Transactional(readOnly=true)	
	public List<Estado> findAllByOrderByNome();

}
