package com.sistema.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.sistema.cursomc.dto.ClienteDTO;
import com.sistema.cursomc.model.Cliente;
import com.sistema.cursomc.repositories.ClienteRepository;
import com.sistema.cursomc.resources.exceptions.FieldMessage;

//classe de validação customizada para inserção de um novo cliente para validar o cpf ou cnpj
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteUpdate ann) {

	}

	@Override
	public boolean isValid(ClienteDTO clienteDto, ConstraintValidatorContext context) {
		
		//pegando o id para update na url 
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		//usando um lista de fieldmessage classe auxiliar para mostrar apenas o campo e msg de erro.
		List<FieldMessage> list = new ArrayList<>();
		
		//condição p/testar se o e-mail já existe cadastrado ou não.
		Cliente cliente = clienteRepository.findByEmail(clienteDto.getEmail());
		
		if(cliente !=null && !cliente.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "E-mail já existente"));
		}
		
		//código auxiliar para que o framework consiga fazer a verificação e validação do codigo acima.
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}
