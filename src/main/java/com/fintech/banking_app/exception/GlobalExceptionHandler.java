package com.fintech.banking_app.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	
	//Handle specific exception
	@ExceptionHandler(AccountException.class)
	public ResponseEntity<ErrorDetails> handleAccountException(AccountException exception, WebRequest webRequest){
		ErrorDetails errorDetails = new ErrorDetails(
				LocalDateTime.now(), 
				exception.getMessage(), 
				webRequest.getDescription(false), "ACCOUNT_NOT_FOUND");
		
		
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
					
	}
	
	//Handle generic exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGenericException(Exception exception, WebRequest webRequest){
		
		ErrorDetails errorDetails = new ErrorDetails(
		LocalDateTime.now(), 
		exception.getMessage(),
		webRequest.getDescription(false),
		"INTERNAL_SEDRVER_ERROR"
		);
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> handleValidationErrors(
	        MethodArgumentNotValidException exception,
	        HttpServletRequest request) {


	    String message = exception.getBindingResult()
	            .getFieldErrors()
	            .get(0)
	            .getDefaultMessage();


	    ErrorDetails errorDetails = new ErrorDetails(
	            LocalDateTime.now(),
	            message,
	            request.getRequestURI(),
	            "VALIDATION_ERROR"
	    );


	    return new ResponseEntity<>(
	            errorDetails,
	            HttpStatus.BAD_REQUEST
	    );
	}
}

