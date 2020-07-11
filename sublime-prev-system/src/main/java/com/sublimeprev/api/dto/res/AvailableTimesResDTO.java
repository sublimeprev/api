package com.sublimeprev.api.dto.res;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AvailableTimesResDTO {

	@JsonFormat(pattern = "HH:mm")
	private LocalTime time;
	private int maxLimit;
	private int filled;
	private boolean full;
	private boolean hasTrainingAvailable;

	private AvailableTimesResDTO(LocalTime time, Integer maxLimit, int filled, boolean hasTrainingAvailable) {
		this.time = time;
		this.maxLimit = maxLimit;
		this.filled = filled;
		this.full = maxLimit == null ? false : filled >= maxLimit;
		this.hasTrainingAvailable = hasTrainingAvailable;
	}

	public static AvailableTimesResDTO of(LocalTime time, Integer maxLimit, int filled, boolean hasTrainingAvailable) {
		return new AvailableTimesResDTO(time, maxLimit, filled, hasTrainingAvailable);
	}
}
