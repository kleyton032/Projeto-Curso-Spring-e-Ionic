package com.sistema.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.cursomc.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}
