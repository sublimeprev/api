package com.sublimeprev.api.dto.req;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class MyScheduledWorkoutReqDTO {

	@NotNull
	private LocalDate date;
	@NotNull
	@JsonFormat(pattern = "HH:mm")
	private LocalTime time;
	private Long idTypeTraining;
}
