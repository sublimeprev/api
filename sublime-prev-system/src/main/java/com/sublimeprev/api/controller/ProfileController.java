package com.sublimeprev.api.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sublimeprev.api.dto.req.ChangePasswordDTO;
import com.sublimeprev.api.dto.req.CreateUserReqDTO;
import com.sublimeprev.api.dto.req.ProfileReqDTO;
import com.sublimeprev.api.dto.req.ResetPasswordDTO;
import com.sublimeprev.api.dto.res.UserResDTO;
import com.sublimeprev.api.service.ProfileService;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {

	private final ProfileService profileService;

	@PreAuthorize("isAuthenticated()")
	@ApiOperation(value = "Returns Spring Security authenticated user", response = Void.class)
	@GetMapping("/me")
	public UserResDTO me() {
		return UserResDTO.of(this.profileService.findAuthenticatedUser());
	}

	@PreAuthorize("isAuthenticated()")
	@ApiOperation(value = "Update authenticated user", response = Void.class)
	@PutMapping("/me")
	public UserResDTO updateProfile(@Valid @RequestBody ProfileReqDTO dto) {
		return UserResDTO.of(this.profileService.updateProfile(dto));
	}

	@PreAuthorize("isAuthenticated()")
	@ApiOperation(value = "Remove access token and refresh token from token store", response = Void.class)
	@DeleteMapping(value = "/logout")
	public void logout() {
		this.profileService.logout();
	}

	@GetMapping("/forgot-password")
	public String forgotPassword(String username) {
		this.profileService.forgotPassword(username);
		return "Um link de recuperação de senha foi enviado para o seu e-mail.";
	}

	@ApiOperation(value = "Reset password", notes = "Change user password passing the new password and the token sent by email", response = Void.class)
	@PutMapping("/reset-password")
	public void resetPassword(@Valid @RequestBody ResetPasswordDTO dto) {
		this.profileService.resetPassword(dto.getToken(), dto.getNewPassword(), dto.getConfirmation());
	}

	@PreAuthorize("isAuthenticated()")
	@ApiOperation(value = "Change autheticated user password", response = Void.class)
	@PutMapping("/change-password")
	public void changePassword(@Valid @RequestBody ChangePasswordDTO dto) {
		this.profileService.changePassword(dto.getOldPassword(), dto.getNewPassword(), dto.getConfirmation());
	}

	@PostMapping("/sign-up")
	public UserResDTO signUp(@Valid @RequestBody CreateUserReqDTO newUser) {
		return UserResDTO.of(this.profileService.signUp(newUser));
	}

	@GetMapping("/confirm-email/{token}")
	public String confirmEmail(@PathVariable("token") String token) {
		this.profileService.confirmEmail(token);
		return "E-mail confirmado com sucesso.";
	}

	@PreAuthorize("isAuthenticated()")
	@ApiOperation(value = "Save firebase token", response = Void.class)
	@PutMapping("/save-token")
	public void saveFirebaseToken(String token) {
		this.profileService.saveFirebaseToken(token);
	}
}
