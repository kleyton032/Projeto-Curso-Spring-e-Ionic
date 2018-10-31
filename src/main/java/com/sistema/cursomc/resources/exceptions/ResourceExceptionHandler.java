package com.sistema.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.sistema.cursomc.services.exceptions.AuthorizationException;
import com.sistema.cursomc.services.exceptions.DataIntegrityException;
import com.sistema.cursomc.services.exceptions.FileException;
import com.sistema.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	 public ResponseEntity<StandarError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		 
		StandarError error = new StandarError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		 
	 }
	
	@ExceptionHandler(DataIntegrityException.class)
	 public ResponseEntity<StandarError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		 
		StandarError error = new StandarError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Integridade de dados", e.getMessage(), request.getRequestURI());
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		 
	 }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandarError> validation (MethodArgumentNotValidException e, HttpServletRequest request){
		
		//utilizando a classe ValidationError
		ValidationError error = new ValidationError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", e.getMessage(), request.getRequestURI());
		
		//fazendo o for para pecorrer todos os erros da lista
		for(FieldError x: e.getBindingResult().getFieldErrors()) {
			//setando o método set da classe validationError
			error.setListMessage(x.getField(), x.getDefaultMessage());
			
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		
	}
	
	@ExceptionHandler(AuthorizationException.class)
	 public ResponseEntity<StandarError> authorization(AuthorizationException e, HttpServletRequest request){
		 
		StandarError error = new StandarError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Acesso negado", e.getMessage(), request.getRequestURI());
		 return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
		 
	 }
	
	//exceção personalizada da FileException, exceção criada adequadamente para retorno do httpstatus
	@ExceptionHandler(FileException.class)
	 public ResponseEntity<StandarError> file(FileException e, HttpServletRequest request){
		 
		StandarError error = new StandarError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro de arquivo", e.getMessage(), request.getRequestURI());
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		 
	 }
	
	//exceção personalizada da amazonService
	@ExceptionHandler(AmazonServiceException.class)
	 public ResponseEntity<StandarError> amazonService(AmazonServiceException e, HttpServletRequest request){
		 
		HttpStatus status = HttpStatus.valueOf(e.getErrorCode());
		StandarError error = new StandarError(System.currentTimeMillis(), status.value(), "Erro Amazon Service", e.getMessage(), request.getRequestURI());
		 return ResponseEntity.status(status).body(error);
		 
	 }
	
	//exceção personalizada da amazonClient
	@ExceptionHandler(AmazonClientException.class)
	 public ResponseEntity<StandarError> amazonClient(AmazonClientException e, HttpServletRequest request){
		 
		StandarError error = new StandarError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro Amazon Client", e.getMessage(), request.getRequestURI());
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		 
	 }
	

	@ExceptionHandler(AmazonS3Exception.class)
	 public ResponseEntity<StandarError> amazonS3(AmazonS3Exception e, HttpServletRequest request){
		 
		StandarError error = new StandarError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro Amazon S3", e.getMessage(), request.getRequestURI());
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		 
	 }
	
	
	
}
