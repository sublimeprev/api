package com.sublimeprev.api.bases;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Iterator;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
	
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	public ConstraintViolationExceptionResponse unknownException(MethodArgumentNotValidException ex, WebRequest req) {
		HashMap<String, String> exceptions = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			exceptions.put(error.getField(), error.getDefaultMessage());
		});
		return new ConstraintViolationExceptionResponse(exceptions);
	}
	
	@ExceptionHandler(value = { ConstraintViolationException.class })
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	public ConstraintViolationExceptionResponse unknownException(ConstraintViolationException ex, WebRequest req) {
		return new ConstraintViolationExceptionResponse(getMessagesFromConstraintViolationException(ex));
	}

	@ExceptionHandler(value = { TransactionSystemException.class })
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	public ConstraintViolationExceptionResponse unknownException(TransactionSystemException ex, WebRequest req) {
		return new ConstraintViolationExceptionResponse(getMessagesFromConstraintViolationException((ConstraintViolationException) ex.getMostSpecificCause()));
	}

	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	public HashMap<String, String> unknownException(DataIntegrityViolationException ex, WebRequest req) {
		return getMessagesFromConstraintViolationException((SQLIntegrityConstraintViolationException) ex.getMostSpecificCause());
	}

	@Data
	@AllArgsConstructor
	private class ConstraintViolationExceptionResponse {
		private HashMap<String, String> exceptions;
	}
	
	private static HashMap<String, String> getMessagesFromConstraintViolationException(ConstraintViolationException e) {
		HashMap<String, String> map = new HashMap<>();
		Iterator<ConstraintViolation<?>> iterator = e.getConstraintViolations().iterator();
		while (iterator.hasNext()) {
			ConstraintViolation<?> constraintViolation = iterator.next();
			map.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
		}
		return map;
	}

	private static HashMap<String, String> getMessagesFromConstraintViolationException(SQLIntegrityConstraintViolationException e) {
		HashMap<String, String> map = new HashMap<>();
		map.put("message", e.getMessage());
		return map;
	}

}
