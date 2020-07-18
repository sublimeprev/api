package com.sublimeprev.api.dto.req;

import com.sublimeprev.api.domain.ProcessStatus;
import com.sublimeprev.api.model.ProcessMother;

import lombok.Data;

@Data
public class ProcessMotherReqDTO {
	
	private Long id;
	private String observation;
	private String requirements;
	private ProcessStatus status;
	private Long idMother;

	public ProcessMother toEntity(ProcessMother entity) {
		entity.setId(this.id);
		entity.setObservation(this.observation);
		entity.setRequirements(this.requirements);
		entity.setStatus(this.status);
		return entity;
	}

}
