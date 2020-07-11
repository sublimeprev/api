package com.sublimeprev.api.dto.res;

import java.time.LocalDate;

import com.sublimeprev.api.model.User;

import lombok.Data;

@Data
public class UserAppResDTO {
	
	private String username;
	private String email;
	private String name;
	private String city;
	private String phone;
	private LocalDate birthdate;

	private UserAppResDTO(User entity) {
		this.username = entity.getUsername();
		this.email = entity.getEmail();
		this.name = entity.getName();
		this.city = entity.getCity();
		this.phone = entity.getPhone();
		this.birthdate = entity.getBirthdate();
	}

	public static UserAppResDTO of(User entity) {
		return entity == null ? null : new UserAppResDTO(entity);
	}
}