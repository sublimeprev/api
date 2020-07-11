package com.sublimeprev.api.dto.req;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sublimeprev.api.model.User;

import lombok.Data;

@Data
public class ProfileReqDTO {

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

	public User toEntity(User entity) {
		entity.setEmail(this.email);
		entity.setName(this.name);
		entity.setCity(this.city);
		entity.setPhone(this.phone);
		entity.setBirthdate(this.birthdate);
		return entity;
	}
}
