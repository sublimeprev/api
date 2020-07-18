package com.sublimeprev.api.dto.res;

import java.time.LocalDateTime;

import com.sublimeprev.api.domain.ProcessStatus;
import com.sublimeprev.api.model.ProcessMother;

import lombok.Data;

@Data
public class ProcessMotherResDTO {
	private Long id;
	private boolean deleted;
	private String createdBy;
	private String updatedBy;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String observation;
	private String requirements;
	private ProcessStatus status;
	private Long idMother;

	private ProcessMotherResDTO(ProcessMother entity) {
		this.id = entity.getId();
		this.deleted = entity.isDeleted();
		this.createdBy = entity.getCreatedBy();
		this.updatedBy = entity.getUpdatedBy();
		this.createdAt = entity.getCreatedAt();
		this.updatedAt = entity.getUpdatedAt();
		this.observation = entity.getObservation();
		this.requirements = entity.getRequirements();
		this.status = entity.getStatus();
		this.setIdMother(entity.getMother().getId());
	}

	public static ProcessMotherResDTO of(ProcessMother entity) {
		return entity == null ? null : new ProcessMotherResDTO(entity);
	}
}
