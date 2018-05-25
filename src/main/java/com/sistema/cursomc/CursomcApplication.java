package com.sistema.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sistema.cursomc.model.Categoria;
import com.sistema.cursomc.model.Cidade;
import com.sistema.cursomc.model.Cliente;
import com.sistema.cursomc.model.Endereco;
import com.sistema.cursomc.model.Estado;
import com.sistema.cursomc.model.ItemPedido;
import com.sistema.cursomc.model.Pagamento;
import com.sistema.cursomc.model.PagamentoComBoleto;
import com.sistema.cursomc.model.PagamentoComCartao;
import com.sistema.cursomc.model.Pedido;
import com.sistema.cursomc.model.Produto;
import com.sistema.cursomc.model.enums.EstadoPagamento;
import com.sistema.cursomc.model.enums.TipoCliente;
import com.sistema.cursomc.repositories.CategoriaRepository;
import com.sistema.cursomc.repositories.CidadeRepository;
import com.sistema.cursomc.repositories.ClienteRepository;
import com.sistema.cursomc.repositories.EnderecoRepository;
import com.sistema.cursomc.repositories.EstadoRepository;
import com.sistema.cursomc.repositories.ItemPedidoRepository;
import com.sistema.cursomc.repositories.PagamentoRepository;
import com.sistema.cursomc.repositories.PedidoRepository;
import com.sistema.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Kleyton João", "09248275459", "kleyton@hotmail.com",
				TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("34934619", "988888888"));

		Endereco end1 = new Endereco(null, "Rua falcão", "526", "Quadra c0", "53370-000", cli1, c1);
		Endereco end2 = new Endereco(null, "Rua Qualquer", "01", "Sala 0", "65180-000", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("25/05/2018 13:31"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("01/04/2018 09:36"), cli1, end2);
		
		Pagamento pgt1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgt1);
		 
		Pagamento pgt2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("16/04/2018 00:00"), null);
		ped2.setPagamento(pgt2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgt1, pgt2));
		
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

	}
}
