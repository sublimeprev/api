package com.sublimeprev.api.dto.res;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.sublimeprev.api.domain.SchedulingStatus;
import com.sublimeprev.api.model.Scheduling;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class SchedulingResDTO {

	private Long id;
	private String createdBy;
	private String updatedBy;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Long userId;
	private String userDesc;
	private LocalDate date;
	@JsonFormat(pattern = "HH:mm")
	private LocalTime time;
	private SchedulingStatus status;
	private String typeExercise;

	private SchedulingResDTO(Scheduling entity) {
		this.id = entity.getId();
		this.createdBy = entity.getCreatedBy();
		this.updatedBy = entity.getUpdatedBy();
		this.createdAt = entity.getCreatedAt();
		this.updatedAt = entity.getUpdatedAt();
		if (entity.getUser() != null) {
			this.userId = entity.getUser().getId();
			this.userDesc = entity.getUser().getName();
		}
		this.date = entity.getDate();
		this.time = entity.getTime();
		this.status = entity.getStatus();
		this.typeExercise = "";
		if(entity.getTypeExercise() !=null) {
			this.typeExercise = entity.getTypeExercise().getType();
		}
		
	}

	public static SchedulingResDTO of(Scheduling entity) {
		return entity == null ? null : new SchedulingResDTO(entity);
	}
}
