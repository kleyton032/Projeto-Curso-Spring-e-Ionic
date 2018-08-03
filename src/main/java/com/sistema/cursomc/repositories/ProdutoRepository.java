package com.sistema.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.cursomc.model.Categoria;
import com.sistema.cursomc.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	// criando própria consultar JPQL a partir da anotação @query sem utilizar nomes
	// padrões
	// do próprio framework

	@Transactional(readOnly = true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(
			@Param("nome") String nome,
			@Param("categorias") List<Categoria> categorias, 
			Pageable pageRequest);
}
