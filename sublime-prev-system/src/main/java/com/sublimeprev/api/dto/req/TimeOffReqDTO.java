package com.sublimeprev.api.dto.req;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import com.sublimeprev.api.model.TimeOff;

import lombok.Data;

@Data
public class TimeOffReqDTO {

	@NotNull
	private LocalDate date;
	private LocalTime time;

	public TimeOff toEntity(TimeOff entity) {
		entity.setDate(this.date);
		entity.setTime(this.time);
		return entity;
	}
}
