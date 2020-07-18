package com.sublimeprev.api.dto.res;

import java.time.LocalDate;

import com.sublimeprev.api.domain.ProcessStatus;
import com.sublimeprev.api.model.ProcessMother;

import lombok.Data;

@Data
public class ProcessMotherClientResDTO {
	
	private String nameMother;
	private String cpf;
	private LocalDate dateStartProcess;
	private String observation;
	private String requirements;
	private ProcessStatus status;
	
	private ProcessMotherClientResDTO(ProcessMother entity) {
		this.setNameMother(entity.getMother().getMotherName());
		this.setCpf(entity.getMother().getCpf());
		this.setDateStartProcess(entity.getDateStart());
		this.setObservation(entity.getObservation());
		this.setRequirements(entity.getRequirements());
		this.setStatus(entity.getStatus());
		
	}

	public static ProcessMotherClientResDTO of(ProcessMother entity) {
		return entity == null ? null : new ProcessMotherClientResDTO(entity);
	}
}
