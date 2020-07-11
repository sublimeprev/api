package com.sublimeprev.api.dto.req;

import java.time.DayOfWeek;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import com.sublimeprev.api.model.AgendaConfig;

import lombok.Data;

@Data
public class AgendaConfigReqDTO {

	@NotNull
	private DayOfWeek dayOfWeek;
	@NotNull
	private LocalTime time;
	private Integer maxLimit;
	private Long typeExercise;

	public AgendaConfig toEntity(AgendaConfig entity) {
		entity.setDayOfWeek(this.dayOfWeek);
		entity.setTime(this.time);
		entity.setMaxLimit(this.maxLimit);
		return entity;
	}
}
