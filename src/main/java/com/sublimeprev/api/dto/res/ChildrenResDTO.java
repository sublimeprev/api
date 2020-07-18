package com.sublimeprev.api.dto.res;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sublimeprev.api.model.Children;

import lombok.Data;

@Data
public class ChildrenResDTO {
	
	private Long id;
	private boolean deleted;
	private String createdBy;
	private String updatedBy;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String name;
	private LocalDate birthday;
	private LocalDate birthCertificateDate;
	private String registration;
	private Long idMother;

	private ChildrenResDTO(Children entity) {
		this.id = entity.getId();
		this.deleted = entity.isDeleted();
		this.createdBy = entity.getCreatedBy();
		this.updatedBy = entity.getUpdatedBy();
		this.createdAt = entity.getCreatedAt();
		this.updatedAt = entity.getUpdatedAt();
		this.name = entity.getName();
		this.birthday = entity.getBirthday();
		this.birthCertificateDate = entity.getBirthCertificateDate();
		this.registration = entity.getRegistration();
		this.setIdMother(entity.getMother().getId());
	}

	public static ChildrenResDTO of(Children entity) {
		return entity == null ? null : new ChildrenResDTO(entity);
	}
}
