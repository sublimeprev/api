package com.sublimeprev.api.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sublimeprev.api.dto.req.CreateUserReqDTO;
import com.sublimeprev.api.dto.req.ProfileReqDTO;
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

	@PostMapping("/sign-up")
	public UserResDTO signUp(@Valid @RequestBody CreateUserReqDTO newUser) {
		return UserResDTO.of(this.profileService.signUp(newUser));
	}
}
