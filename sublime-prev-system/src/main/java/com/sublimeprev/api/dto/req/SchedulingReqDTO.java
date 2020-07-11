package com.sublimeprev.api.dto.req;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import com.sublimeprev.api.domain.SchedulingStatus;
import com.sublimeprev.api.model.Scheduling;
import com.sublimeprev.api.model.TypeExercise;
import com.sublimeprev.api.model.User;

import lombok.Data;

@Data
public class SchedulingReqDTO {

	@NotNull
	private Long userId;
	@NotNull
	private LocalDate date;
	@NotNull
	private LocalTime time;
	@NotNull
	private SchedulingStatus status;
	private Long idTypeExercise;

	public Scheduling toEntity(Scheduling entity) {
		entity.setUser(this.userId == null ? null : User.builder().id(this.userId).build());
		entity.setTypeExercise(this.idTypeExercise == null ? null : TypeExercise.builder().id(this.idTypeExercise).build());
		entity.setDate(this.date);
		entity.setTime(this.time);
		entity.setStatus(this.status);
		return entity;
	}
}
