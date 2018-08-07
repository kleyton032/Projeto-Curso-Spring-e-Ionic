package com.sistema.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.sistema.cursomc.model.Pedido;

public interface EmailService {

	
	void sendOrderConfirmationEmail(Pedido pedido);
	
	void senderEmail(SimpleMailMessage msg);
}
