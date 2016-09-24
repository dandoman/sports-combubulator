package com.merccann.exception;

import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@Log4j2
public class TopLevelHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler({ RuntimeException.class })
	protected ResponseEntity<Object> catchAllHandler(RuntimeException e,
			WebRequest request) {
		log.error("Internal server error", e);
		ErrorResource error = new ErrorResource();
		error.setMessage("Server error: " + e.getMessage());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return handleExceptionInternal(e, error, headers,
				HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	@ExceptionHandler({ BadArgsException.class })
	protected ResponseEntity<Object> catchBadRequestHandler(RuntimeException e,
			WebRequest request) {
		BadArgsException ba = (BadArgsException) e;
		log.error(ba);
		ErrorResource error = new ErrorResource();
		error.setMessage(ba.getMessage());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return handleExceptionInternal(e, error, headers,
				HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ NotFoundException.class })
	protected ResponseEntity<Object> catchNotFoundHandler(RuntimeException e,
			WebRequest request) {
		NotFoundException nfe = (NotFoundException) e;
		log.error(nfe);
		ErrorResource error = new ErrorResource();
		error.setMessage(nfe.getMessage());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return handleExceptionInternal(e, error, headers,
				HttpStatus.NOT_FOUND, request);
	}
}