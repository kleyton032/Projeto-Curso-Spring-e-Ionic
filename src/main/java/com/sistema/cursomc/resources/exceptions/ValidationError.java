package com.sistema.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

//classe herdando da classe StandError
public class ValidationError extends StandarError {
	
	private static final long serialVersionUID = 1L;
	
	//criando um lista da classe de fielmessage
	private List<FieldMessage> listMessage = new ArrayList<>();

	public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public List<FieldMessage> getListMessage() {
		return listMessage;
	}
	
	//método set alterando o padrão p/mostrar o nome do campo e a mensagem desse campo na lista
	public void setListMessage(String fieldName, String message) {
		listMessage.add(new FieldMessage(fieldName, message));
	}

	
}
