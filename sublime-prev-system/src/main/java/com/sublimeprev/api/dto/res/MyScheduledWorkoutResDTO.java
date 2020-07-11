package com.sublimeprev.api.dto.res;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

import org.springframework.util.StringUtils;

import com.sublimeprev.api.domain.SchedulingStatus;
import com.sublimeprev.api.model.Scheduling;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class MyScheduledWorkoutResDTO {

	private Long id;
	private LocalDate date;
	@JsonFormat(pattern = "HH:mm")
	private LocalTime time;
	private SchedulingStatus status;
	private String dayOfWeek;
	@JsonFormat(pattern = "dd/MM")
	private LocalDate dayMonth;

	private MyScheduledWorkoutResDTO(Scheduling entity) {
		this.id = entity.getId();
		this.date = entity.getDate();
		this.time = entity.getTime();
		this.status = entity.getStatus();
		this.dayMonth = entity.getDate();
		this.dayOfWeek = null;
		if (this.date != null) {
			this.dayOfWeek = StringUtils.capitalize(
					entity.getDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pt-BR")))
					.replace("f", "F");
		}
	}

	public static MyScheduledWorkoutResDTO of(Scheduling entity) {
		return entity == null ? null : new MyScheduledWorkoutResDTO(entity);
	}
}
