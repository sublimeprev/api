package com.sublimeprev.api.config.i18n;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3147030493429042935L;

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Messages message) {
		super(MessageFactory.getMessage(message));
	}

	public ServiceException(Messages message, String... args) {
		super(MessageFactory.getMessage(message, args));
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
