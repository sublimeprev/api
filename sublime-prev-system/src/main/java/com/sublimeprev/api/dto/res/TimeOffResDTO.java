package com.sublimeprev.api.dto.res;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sublimeprev.api.model.TimeOff;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TimeOffResDTO {

	private Long id;
	private LocalDate date;
	@JsonFormat(pattern = "HH:mm")
	private LocalTime time;

	private TimeOffResDTO(TimeOff entity) {
		this.id = entity.getId();
		this.date = entity.getDate();
		this.time = entity.getTime();
	}

	public static TimeOffResDTO of(TimeOff entity) {
		return entity == null ? null : new TimeOffResDTO(entity);
	}
}
