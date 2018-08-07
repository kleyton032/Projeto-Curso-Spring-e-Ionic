package com.sistema.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.sistema.cursomc.model.Pedido;

public interface EmailService {

	//versão de text
	void sendOrderConfirmationEmail(Pedido pedido);
	
	void senderEmail(SimpleMailMessage msg);
	
	//versão html
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void sendHtmlEmail(MimeMessage msg);
}
