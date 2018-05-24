package com.sistema.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.cursomc.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

}
