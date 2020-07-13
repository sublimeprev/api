package com.sublimeprev.api.dto.req;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sublimeprev.api.domain.Role;
import com.sublimeprev.api.model.User;

import lombok.Data;

@Data
public class UserReqDTO {

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
	private Set<Role> roles;
	private String newPassword;
	private String encryptedPassword;
	private String comments;
	
	public User toEntity(User entity) {
		entity.setUsername(this.username);
		entity.setEmail(this.email);
		entity.setName(this.name);
		entity.setCity(this.city);
		entity.setPhone(this.phone);
		entity.setBirthdate(this.birthdate);
		entity.setRoles(this.roles);
		entity.setNewPassword(this.newPassword);
		if(this.encryptedPassword != null) {
			entity.setEncryptedPassword(this.encryptedPassword);
		}
		entity.setComments(this.comments);
		
		return entity;
	}
	
	public User toEntityProfile(User entity) {
		entity.setUsername(this.username);
		entity.setEmail(this.email);
		entity.setName(this.name);
		entity.setCity(this.city);
		entity.setPhone(this.phone);
		entity.setBirthdate(this.birthdate);
		return entity;
	}
}
