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
		 
		 StandarError error = new StandarError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		 
	 }
	
	@ExceptionHandler(DataIntegrityException.class)
	 public ResponseEntity<StandarError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		 
		 StandarError error = new StandarError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		 
	 }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandarError> validation (MethodArgumentNotValidException e, HttpServletRequest request){
		
		//utilizando a classe ValidationError
		ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(),"Erro de validação", System.currentTimeMillis());
		
		//fazendo o for para pecorrer todos os erros da lista
		for(FieldError x: e.getBindingResult().getFieldErrors()) {
			//setando o método set da classe validationError
			error.setListMessage(x.getField(), x.getDefaultMessage());
			
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		
	}
	
	@ExceptionHandler(AuthorizationException.class)
	 public ResponseEntity<StandarError> authorization(AuthorizationException e, HttpServletRequest request){
		 
		 StandarError error = new StandarError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
		 return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
		 
	 }
	
	@ExceptionHandler(FileException.class)
	 public ResponseEntity<StandarError> file(FileException e, HttpServletRequest request){
		 
		 StandarError error = new StandarError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		 
	 }
	
	@ExceptionHandler(AmazonServiceException.class)
	 public ResponseEntity<StandarError> amazonService(AmazonServiceException e, HttpServletRequest request){
		 
		HttpStatus status = HttpStatus.valueOf(e.getErrorCode());
		 StandarError error = new StandarError(status.value(), e.getMessage(), System.currentTimeMillis());
		 return ResponseEntity.status(status).body(error);
		 
	 }
	
	@ExceptionHandler(AmazonClientException.class)
	 public ResponseEntity<StandarError> amazonClient(AmazonClientException e, HttpServletRequest request){
		 
		 StandarError error = new StandarError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		 
	 }
	

	@ExceptionHandler(AmazonS3Exception.class)
	 public ResponseEntity<StandarError> amazonS3(AmazonS3Exception e, HttpServletRequest request){
		 
		 StandarError error = new StandarError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		 
	 }
	
	
	
}
