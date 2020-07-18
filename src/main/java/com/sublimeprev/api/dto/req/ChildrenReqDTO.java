package com.sublimeprev.api.dto.req;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sublimeprev.api.model.Children;

import lombok.Data;

@Data
public class ChildrenReqDTO {

	private Long id;
	@NotBlank
	private String name;
	private LocalDate birthday;
	private LocalDate birthCertificateDate;
	private String registration; 
	@NotNull
	private Long idMother;

	public Children toEntity(Children entity) {
		entity.setId(this.id);
		entity.setName(this.name);
		entity.setBirthday(this.birthday);
		entity.setBirthCertificateDate(this.birthCertificateDate);
		entity.setRegistration(this.registration);
		return entity;
	}
}
