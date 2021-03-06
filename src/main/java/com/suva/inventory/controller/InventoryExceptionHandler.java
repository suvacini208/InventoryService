package com.suva.inventory.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.suva.inventory.domain.Error;

@ControllerAdvice
public class InventoryExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Error> error = ex.getAllErrors().stream().map(err -> new Error(err.getDefaultMessage()))
				.collect(Collectors.toList());
		return new ResponseEntity<Object>(error, headers, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler({ InventoryException.class })
	protected ResponseEntity<Object> handleCartException(InventoryException ex, WebRequest request) {

		Error error = new Error(ex.getMessage());
		return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
