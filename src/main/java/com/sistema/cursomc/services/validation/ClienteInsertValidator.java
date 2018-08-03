package com.sistema.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.sistema.cursomc.dto.ClienteNewDTO;
import com.sistema.cursomc.model.Cliente;
import com.sistema.cursomc.model.enums.TipoCliente;
import com.sistema.cursomc.repositories.ClienteRepository;
import com.sistema.cursomc.resources.exceptions.FieldMessage;
import com.sistema.cursomc.services.validation.utils.CpfOuCnpj;

//classe de validação customizada para inserção de um novo cliente para validar o cpf ou cnpj
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {

	}

	@Override
	public boolean isValid(ClienteNewDTO clienteNewDto, ConstraintValidatorContext context) {
		
		//usando um lista de fieldmessage classe auxiliar para mostrar apenas o campo e msg de erro.
		List<FieldMessage> list = new ArrayList<>();
		
		if(clienteNewDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCod()) 
				&& !CpfOuCnpj.isValidCPF(clienteNewDto.getCpfOuCnpj())) {
					list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido"));
		}
		
		if(clienteNewDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCod()) 
				&& !CpfOuCnpj.isValidCNPJ(clienteNewDto.getCpfOuCnpj())) {
					list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido"));
		}
		
		//condição p/testar se o e-mail já existe cadastrado ou não.
		Cliente cliente = clienteRepository.findByEmail(clienteNewDto.getEmail());
		
		if(cliente !=null) {
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
