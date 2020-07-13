package com.sublimeprev.api.dto.req;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ResetPasswordDTO {

	@NotBlank
	private String token;
	@NotBlank
	private String newPassword;
	@NotBlank
	private String confirmation;
}
