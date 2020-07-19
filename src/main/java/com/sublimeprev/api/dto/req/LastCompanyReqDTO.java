package com.sublimeprev.api.dto.req;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sublimeprev.api.domain.ReasonForDismissal;
import com.sublimeprev.api.model.LastCompany;

import lombok.Data;

@Data
public class LastCompanyReqDTO {
	private Long id;
	@NotBlank
	private String name;
	private boolean unemploymentInsurance;
	private ReasonForDismissal reasonForDismissal;
	private LocalDate dismissalDate;
	private LocalDate admissionDate;
	@NotNull
	private Long idMother;

	public LastCompany toEntity(LastCompany entity) {
		entity.setId(this.id);
		entity.setName(this.name);
		entity.setUnemploymentInsurance(this.unemploymentInsurance);
		entity.setReasonForDismissal(this.reasonForDismissal);
		entity.setDismissalDate(this.dismissalDate);
		entity.setAdmissionDate(this.admissionDate);
		return entity;
	}
}
