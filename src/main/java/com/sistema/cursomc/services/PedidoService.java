package com.sistema.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.cursomc.model.ItemPedido;
import com.sistema.cursomc.model.PagamentoComBoleto;
import com.sistema.cursomc.model.Pedido;
import com.sistema.cursomc.model.enums.EstadoPagamento;
import com.sistema.cursomc.repositories.ItemPedidoRepository;
import com.sistema.cursomc.repositories.PagamentoRepository;
import com.sistema.cursomc.repositories.PedidoRepository;
import com.sistema.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private boletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
//	@Autowired
//	private ProdutoService produtoService;

	public Pedido find(Integer id) {
		Optional<Pedido> objeto = pedidoRepository.findById(id);
		return objeto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido pedido) {
		//garantido que o pedido está nulo para inserção
		pedido.setId(null);
		//instanciando a data e hora do pedido
		pedido.setInstante(new Date());
		//setando cliente
		pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
		//pegando o estado pagamento como pendente, pois se estou cadastrando um novo o status estará como pendente
		pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		//associação que o pagamento conheça o pedido dele
		pedido.getPagamento().setPedido(pedido);
		
		//fazendo a implementação para que a forma de pagmento conhença a data de vencimento
		if(pedido.getPagamento() instanceof PagamentoComBoleto) {
			//criando uma variável do tipo pagamentoComBoleto
			PagamentoComBoleto pgto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, pedido.getInstante());
		}
		
		//salvando o pedido
		pedido = pedidoRepository.save(pedido);
		//salvando pagamento
		pagamentoRepository.save(pedido.getPagamento());
		
		for(ItemPedido itemPedido : pedido.getItens()) {
			itemPedido.setDesconto(0.0);
			//setando produto
			itemPedido.setProduto(produtoService.find(itemPedido.getProduto().getId()));
			//instanciando o preço
			itemPedido.setPreco(itemPedido.getProduto().getPreco());
			itemPedido.setPedido(pedido);
		}
		itemPedidoRepository.saveAll(pedido.getItens());
		System.out.println(pedido);
		return pedido;
	}

}
