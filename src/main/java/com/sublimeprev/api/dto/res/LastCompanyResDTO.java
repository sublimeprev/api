package com.sublimeprev.api.dto.res;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sublimeprev.api.domain.ReasonForDismissal;
import com.sublimeprev.api.model.LastCompany;

import lombok.Data;

@Data
public class LastCompanyResDTO {
	private Long id;
	private boolean deleted;
	private String createdBy;
	private String updatedBy;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String name;
	private boolean unemploymentInsurance;
	private ReasonForDismissal reasonForDismissal;
	private LocalDate dismissalDate;
	private LocalDate admissionDate;
	private Long idMother;

	private LastCompanyResDTO(LastCompany entity) {
		this.id = entity.getId();
		this.deleted = entity.isDeleted();
		this.createdBy = entity.getCreatedBy();
		this.updatedBy = entity.getUpdatedBy();
		this.createdAt = entity.getCreatedAt();
		this.updatedAt = entity.getUpdatedAt();
		this.name = entity.getName();
		this.unemploymentInsurance = entity.isUnemploymentInsurance();
		this.reasonForDismissal = entity.getReasonForDismissal();
		this.dismissalDate = entity.getDismissalDate();
		this.admissionDate = entity.getAdmissionDate();
//		this.setIdMother(entity.getMother().getId());
	}

	public static LastCompanyResDTO of(LastCompany entity) {
		return entity == null ? null : new LastCompanyResDTO(entity);
	}
}
