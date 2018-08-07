package com.sistema.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.sistema.cursomc.model.Pedido;

public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	//método para povoar o preenchimento do HTML e retornar uma String
	protected String htmlFromTemplatePedido(Pedido pedido) {
		Context context = new Context();
		context.setVariable("pedido", pedido);
		return templateEngine.process("email/confirmacaoPedido", context);
		
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
		//try para caso de exceção no método abaixo envia email texto plano 
		//caso entre na exceção MessagingException 
		try {
		MimeMessage msg = prepareMimeMessageFromPedido(pedido);
		sendHtmlEmail(msg);
		}catch (MessagingException ex){
			//passando o método do e-mail texto plano caso de erro no html
			sendOrderConfirmationEmail(pedido);
		}
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
		messageHelper.setTo(pedido.getCliente().getEmail());
		messageHelper.setFrom(sender);
		messageHelper.setSubject("Pedido Confirmado: " + pedido.getId());
		messageHelper.setSentDate(new Date(System.currentTimeMillis()));
		messageHelper.setText(htmlFromTemplatePedido(pedido), true);
		return message;
		
		
	}

}
