package com.devsuperior.dslearnbds.controllers.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import com.devsuperior.dslearnbds.services.exceptions.DatabaseException;
import com.devsuperior.dslearnbds.services.exceptions.ForbiddenException;
import com.devsuperior.dslearnbds.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dslearnbds.services.exceptions.UnauthorizedExcpetion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Database Exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError err = new ValidationError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Validation Exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		for(FieldError f : e.getBindingResult().getFieldErrors()){
			err.addError(f.getField(), f.getDefaultMessage());
		}

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<OauthCustomError> forbidden (ForbiddenException e, HttpServletRequest request){
		OauthCustomError err = new OauthCustomError("Forbidden", e.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}

	@ExceptionHandler(UnauthorizedExcpetion.class)
	public ResponseEntity<OauthCustomError> unauthorized (UnauthorizedExcpetion e, HttpServletRequest request){
		OauthCustomError err = new OauthCustomError("Unauthorized", e.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
	}
}
