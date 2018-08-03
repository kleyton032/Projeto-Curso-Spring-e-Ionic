package com.sistema.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.cursomc.dto.ProdutoDTO;
import com.sistema.cursomc.model.Produto;
import com.sistema.cursomc.resources.utils.URL;
import com.sistema.cursomc.services.ProdutoService;


@RestController
@RequestMapping(value="/produtos")
public class ProdutoResources {

	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(value= "/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto objeto = produtoService.find(id);		
		return ResponseEntity.ok().body(objeto);
	}
	
	//método para consulta de nome de produtos e suas categorias
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(name="nome", defaultValue="0") String nome, 
			@RequestParam(name="categorias", defaultValue="0") String categorias, 
			@RequestParam(name="page", defaultValue="0") Integer page, 
			@RequestParam(name="linesPerPages", defaultValue="24")Integer linesPerPages, 
			@RequestParam(name="ordeBy", defaultValue="nome")String ordeBy, 
			@RequestParam(name="direction", defaultValue="ASC")String direction) {
		
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		
		Page<Produto> list = produtoService.search(nomeDecoded, ids, page, linesPerPages, ordeBy, direction);
		Page<ProdutoDTO> listDto = list.map(produto -> new ProdutoDTO(produto));
		return ResponseEntity.ok().body(listDto);
	}
	
	
	
}
