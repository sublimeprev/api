package com.sublimeprev.api.dto.req;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sublimeprev.api.model.User;

import lombok.Data;

@Data
public class CreateUserReqDTO {

	@NotBlank
	private String username;
	@NotBlank
	private String email;
	@NotBlank
	private String name;
	@NotBlank
	private String city;
	@NotBlank
	private String phone;
	@NotNull
	private LocalDate birthdate;
	@NotBlank
	private String password;
	@NotBlank
	private String passwordConfirmation;

	public User toEntity(User entity) {
		entity.setUsername(this.username);
		entity.setEmail(this.email);
		entity.setName(this.name);
		entity.setCity(this.city);
		entity.setPhone(this.phone);
		entity.setBirthdate(this.birthdate);
		return entity;
	}
}
