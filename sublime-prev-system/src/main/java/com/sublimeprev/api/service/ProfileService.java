package com.sublimeprev.api.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sublimeprev.api.config.i18n.ServiceException;
import com.sublimeprev.api.config.security.AuthUtil;
import com.sublimeprev.api.dto.req.CreateUserReqDTO;
import com.sublimeprev.api.dto.req.ProfileReqDTO;
import com.sublimeprev.api.model.User;
import com.sublimeprev.api.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class ProfileService {

	private final UserRepository userRepository;
	private final TokenStore tokenStore;
	private final EmailService emailService;
	private final PasswordService passwordService;

	public User findAuthenticatedUser() {
		return this.userRepository.findById(AuthUtil.getUserId())
				.orElseThrow(() -> new ServiceException("Usuário deve estar autenticado."));
	}

	public void logout() {
		if (SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2Authentication) {
			OAuth2AccessToken accessToken = tokenStore
					.getAccessToken((OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication());
			if (accessToken != null && accessToken.getRefreshToken() != null) {
				this.tokenStore.removeAccessTokenUsingRefreshToken(accessToken.getRefreshToken());
				this.tokenStore.removeRefreshToken(accessToken.getRefreshToken());
			}
		}
	}

	public void forgotPassword(String username) {
		User user = this.userRepository.findByUsername(username)
				.orElseThrow(() -> new ServiceException("Usuário não cadastrado"));
		user.setResetToken(AuthUtil.generateRandomToken() + user.getId().toString());
		user = userRepository.save(user);
		this.emailService.sendForgotPasswordEmail(user);
	}

	public void resetPassword(String resetPasswordToken, String newPassword, String confirmation) {
		User user = userRepository.findFirstByResetToken(resetPasswordToken)
				.orElseThrow(() -> new ServiceException("O link de recuperação de senha utilizado não é mais válido."));
		this.passwordService.newPasswordValidation(newPassword, confirmation);
		user.setEncryptedPassword(passwordService.encode(newPassword));
		user.setResetToken(null);
		this.userRepository.save(user);
	}

	public void changePassword(String oldPassword, String newPassword, String confirmation) {
		User user = this.findAuthenticatedUser();
		this.passwordService.passwordValidation(user.getEncryptedPassword(), oldPassword, newPassword, confirmation);
		user.setEncryptedPassword(this.passwordService.encode(newPassword));
		this.userRepository.save(user);
	}

	public User updateProfile(ProfileReqDTO dto) {
		User user = this.findAuthenticatedUser();
		return this.userRepository.save(dto.toEntity(user));
	}

	public User signUp(CreateUserReqDTO dto) {
		log.info("Iniciando cadastro de usuario: " + dto.getEmail());
		if (this.userRepository.findByUsername(dto.getUsername()).isPresent())
			throw new ServiceException("Nome de usuário já cadastrado no sistema.");
		this.passwordService.newPasswordValidation(dto.getPassword(), dto.getPasswordConfirmation());
		User user = dto.toEntity(new User());
		user.setEncryptedPassword(this.passwordService.encode(dto.getPassword()));
		user.setEmailVerified(false);
		user.setResetToken(AuthUtil.generateRandomToken() + user.getUsername().toString());
		user = this.userRepository.save(user);
		this.emailService.sendConfirmEmail(user);
		log.info("Usuário cadastrado com sucesso username: " + user.getUsername());
		return user;
	}

	public void confirmEmail(String token) {
		if (StringUtils.isEmpty(token))
			throw new ServiceException("O link de confirmação de e-mail utilizado não é mais válido.");
		User user = userRepository.findFirstByResetToken(token).orElseThrow(
				() -> new ServiceException("O link de confirmação de e-mail utilizado não é mais válido."));
		user.setEmailVerified(true);
		user.setResetToken(null);
		user = this.userRepository.save(user);
	}

	public void saveFirebaseToken(String firebaseToken) {
		if (StringUtils.hasText(firebaseToken) && !firebaseToken.equals("null")) {
			User user = this.findAuthenticatedUser();
			user.setFirebaseToken(firebaseToken);
			this.userRepository.save(user);
		}
	}
}
