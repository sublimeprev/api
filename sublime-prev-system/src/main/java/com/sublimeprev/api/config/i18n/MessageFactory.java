package com.sublimeprev.api.config.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.sublimeprev.api.config.ContextProvider;

public class MessageFactory {

	private static MessageSource messageSource;

	private static MessageSource getMessageSource() {
		if (messageSource == null)
			messageSource = ContextProvider.getBean("messageSource", MessageSource.class);
		return messageSource;
	}

	public static String getMessage(Messages message) {
		return getMessageSource().getMessage(message.toString(), null, LocaleContextHolder.getLocale());
	}

	public static String getMessage(Messages message, String... args) {
		return getMessageSource().getMessage(message.toString(), args, LocaleContextHolder.getLocale());
	}
}
