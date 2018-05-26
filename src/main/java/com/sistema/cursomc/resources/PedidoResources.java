package com.sistema.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.cursomc.model.Pedido;
import com.sistema.cursomc.services.PedidoService;


@RestController
@RequestMapping(value="/pedidos")
public class PedidoResources {

	@Autowired
	private PedidoService pedidoService;
	
	@RequestMapping(value= "/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido objeto = pedidoService.find(id);		
		return ResponseEntity.ok().body(objeto);
	}
	
	
	
}
