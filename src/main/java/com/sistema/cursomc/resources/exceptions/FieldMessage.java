package com.sistema.cursomc.resources.exceptions;

import java.io.Serializable;

//classe auxiliar para o retorno da messagem do status badrequest do erro de validação
public class FieldMessage implements Serializable{


	private static final long serialVersionUID = 1L;
	
	//nome do campo
	private String fieldName;
	//mensagem do erro desse campo
	private String message;
	
	public FieldMessage(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;	
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
