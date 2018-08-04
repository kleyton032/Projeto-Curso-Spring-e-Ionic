package com.sistema.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.sistema.cursomc.model.PagamentoComBoleto;

@Service
public class boletoService {
	
	//caso se estiver usando um web service para geração do boleto o código ficar aqui nesse método
	
	
	//método para colocar 7 dias de vencimento após o instanteDoPedido
	public void preencherPagamentoComBoleto(PagamentoComBoleto pgto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pgto.setDataVencimento(cal.getTime());
	}
	
	
}
