package com.sistema.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sistema.cursomc.model.Categoria;
import com.sistema.cursomc.repositories.CategoriaRepository;
import com.sistema.cursomc.services.exceptions.DataIntegrityException;
import com.sistema.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria find(Integer id) {
		Optional<Categoria> objeto = categoriaRepository.findById(id);
		return objeto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));

	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepository.save(categoria);
		
	}

	public Categoria update(Categoria categoria) {
		find(categoria.getId());
		return categoriaRepository.save(categoria);
	}

	public void delete(Integer id) {
		find(id);
		try {
		categoriaRepository.deleteById(id);
		
		} catch (DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir categoria com produtos associados");
		}
		
	}

	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

}
