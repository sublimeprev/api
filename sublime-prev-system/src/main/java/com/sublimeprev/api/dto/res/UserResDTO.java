package com.sublimeprev.api.dto.res;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
	private List<TypeExerciseResDTO> listTypeExercise;
	private List<Long> typeExercise;
	private String comments;

	private UserResDTO(User entity, List<TypeExerciseResDTO> listTypeExercise) {
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
		this.listTypeExercise = listTypeExercise;
		if(!listTypeExercise.isEmpty() || listTypeExercise != null) {
			List<Long> idsTypeExercise = new ArrayList<>();
			listTypeExercise.forEach(type -> {
				idsTypeExercise.add(type.getId());
				;
			});
			this.typeExercise = idsTypeExercise;
		}
		this.comments = entity.getComments();
		
		
	}

	public static UserResDTO of(User entity) {
		return entity == null ? null : new UserResDTO(entity, listTypeExercise(entity));
	}
	
	private static List<TypeExerciseResDTO> listTypeExercise(User entity){
		List<TypeExerciseResDTO> list = TypeExerciseResDTO.listTypeExerciseResDTO(entity.getTypesExercises());
		return list;
	}
}

