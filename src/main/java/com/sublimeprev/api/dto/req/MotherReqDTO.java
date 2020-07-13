package com.sublimeprev.api.dto.req;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sublimeprev.api.domain.MaritalStatus;
import com.sublimeprev.api.domain.Schooling;
import com.sublimeprev.api.model.Mother;

import lombok.Data;

@Data
public class MotherReqDTO {
	private String email;
	@NotBlank
	private String name;
	private String phone;
	@NotNull
	private LocalDate birthdate;
	@NotBlank
	private String cpf;
	private String rg;
	private String pis;
	private MaritalStatus maritalStatus;
	private Schooling schooling;
	private String fatherName;
	private String motherName;
	
	public Mother toEntity(Mother entity) {
		
		entity.setName(this.name);
		entity.setEmail(this.email);
		entity.setPhone(this.phone);
		entity.setBirthdate(this.birthdate);
		entity.setCpf(this.cpf);
		entity.setRg(this.rg);
		entity.setPis(this.pis);
		entity.setMaritalStatus(this.maritalStatus);
		entity.setSchooling(this.schooling);
		entity.setFatherName(this.fatherName);
		entity.setMotherName(this.motherName);
		
		return entity;
	}
}
