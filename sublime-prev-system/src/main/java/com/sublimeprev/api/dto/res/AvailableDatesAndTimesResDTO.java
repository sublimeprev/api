package com.sublimeprev.api.dto.res;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class AvailableDatesAndTimesResDTO {

	private LocalDate date;
	private List<AvailableTimesResDTO> times;
	private boolean full;
	private boolean hasTrainingAvailable;

	private AvailableDatesAndTimesResDTO(LocalDate date, List<AvailableTimesResDTO> times) {
		this.date = date;
		this.times = times;
		if (times != null) {
			this.full = times.stream().filter(item -> !item.isFull()).count() == 0;
		}
		this.hasTrainingAvailable = false;
		times.forEach(time -> {
			if(time.isHasTrainingAvailable()) {
				this.hasTrainingAvailable = true;
			}
		});
	}

	public static AvailableDatesAndTimesResDTO of(LocalDate date, List<AvailableTimesResDTO> times) {
		return new AvailableDatesAndTimesResDTO(date, times);
	}
}
