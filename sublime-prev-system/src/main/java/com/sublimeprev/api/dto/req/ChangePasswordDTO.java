package com.sublimeprev.api.dto.req;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ChangePasswordDTO {

	@NotBlank
	private String oldPassword;
	@NotBlank
	private String newPassword;
	@NotBlank
	private String confirmation;
}
