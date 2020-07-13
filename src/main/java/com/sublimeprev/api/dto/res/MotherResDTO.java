package com.sublimeprev.api.dto.res;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sublimeprev.api.domain.MaritalStatus;
import com.sublimeprev.api.domain.Schooling;
import com.sublimeprev.api.model.Mother;

import lombok.Data;

@Data
public class MotherResDTO {
	private Long id;
	private boolean deleted;
	private String createdBy;
	private String updatedBy;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String email;
	private String name;
	private String phone;
	private LocalDate birthdate;
	private String cpf;
	private String rg;
	private String pis;
	private MaritalStatus maritalStatus;
	private Schooling schooling;
	private String fatherName;
	private String motherName;
	
	private MotherResDTO(Mother entity) {
		this.id = entity.getId();
		this.deleted = entity.isDeleted();
		this.createdBy = entity.getCreatedBy();
		this.updatedBy = entity.getUpdatedBy();
		this.createdAt = entity.getCreatedAt();
		this.updatedAt = entity.getUpdatedAt();
		this.email = entity.getEmail();
		this.name = entity.getName();
		this.phone = entity.getPhone();
		this.birthdate = entity.getBirthdate();	
		this.cpf = entity.getCpf();
		this.rg = entity.getRg();
		this.pis = entity.getPis();
		this.maritalStatus = entity.getMaritalStatus();
		this.schooling = entity.getSchooling();
		this.fatherName = entity.getFatherName();
		this.motherName = entity.getMotherName();
	}

	public static MotherResDTO of(Mother entity) {
		return entity == null ? null : new MotherResDTO(entity);
	}
}
