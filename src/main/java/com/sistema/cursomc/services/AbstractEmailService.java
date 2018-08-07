package com.sistema.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.sistema.cursomc.model.Pedido;

public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		
		SimpleMailMessage msg = prepareSimpleMailMessageFromPedido(pedido);
		senderEmail(msg);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage msg = new SimpleMailMessage();
		//e-mail destinatário
		msg.setTo(pedido.getCliente().getEmail());
		//e-mail remetente
		msg.setFrom(sender);
		//assunto do email
		msg.setSubject("Pedido Confirmado: " + pedido.getId());
		//pegando a data do servidor
		msg.setSentDate(new Date(System.currentTimeMillis()));
		//pegando o formato do e-mail pelo toString
		msg.setText(pedido.toString());
		
		return msg;
	}

}
