package com.sublimeprev.api.dto.res;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.sublimeprev.api.domain.Role;
import com.sublimeprev.api.model.User;

import lombok.Data;

@Data
public class UserResDTO {

	private Long id;
	private boolean deleted;
	private String createdBy;
	private String updatedBy;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String username;
	private String email;
	private String name;
	private String city;
	private String phone;
	private LocalDate birthdate;
	private String newPassword;
	private boolean admin;
	private Set<Role> roles;
	private String comments;

	private UserResDTO(User entity) {
		this.id = entity.getId();
		this.deleted = entity.isDeleted();
		this.createdBy = entity.getCreatedBy();
		this.updatedBy = entity.getUpdatedBy();
		this.createdAt = entity.getCreatedAt();
		this.updatedAt = entity.getUpdatedAt();
		this.username = entity.getUsername();
		this.email = entity.getEmail();
		this.name = entity.getName();
		this.city = entity.getCity();
		this.phone = entity.getPhone();
		this.birthdate = entity.getBirthdate();
		this.newPassword = entity.getNewPassword();
		this.roles = entity.getRoles();
		this.admin =  entity.getRoles() != null && entity.getRoles().contains(Role.ADMIN);
		this.comments = entity.getComments();
		
		
	}

	public static UserResDTO of(User entity) {
		return entity == null ? null : new UserResDTO(entity);
	}
	
}

