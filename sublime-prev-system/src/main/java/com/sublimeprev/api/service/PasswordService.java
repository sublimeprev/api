package com.sublimeprev.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sublimeprev.api.config.i18n.ServiceException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PasswordService {

	private final PasswordEncoder passwordEncoder;

	public String encode(String rawPassword) {
		if (StringUtils.containsWhitespace(rawPassword))
			throw new ServiceException("A senha não pode conter espaços em branco.");
		return this.passwordEncoder.encode(rawPassword);
	}

	public void passwordValidation(String storedPass, String currentPass, String newPass, String confirmation) {
		this.oldPasswordValidation(storedPass, currentPass);
		this.newPasswordValidation(newPass, confirmation);
	}

	public void oldPasswordValidation(String storedPass, String currentPass) {
		if (StringUtils.isEmpty(currentPass))
			throw new ServiceException("A senha atual deve ser informada. ");
		if (!this.passwordEncoder.matches(currentPass, storedPass))
			throw new ServiceException("A senha atual é inválida.");
	}

	public void newPasswordValidation(String newPass, String confirmation) {
		if (StringUtils.isEmpty(newPass))
			throw new ServiceException("A senha deve ser informada.");
		if (StringUtils.isEmpty(confirmation))
			throw new ServiceException("A confirmação da senha deve ser informada.");
		if (StringUtils.containsWhitespace(newPass))
			throw new ServiceException("A senha não pode conter espaços em branco.");
		if (!newPass.equals(confirmation))
			throw new ServiceException("A senha informada está diferente da confirmação da senha.");
	}
}
