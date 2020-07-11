package com.sublimeprev.api.dto.req;

import java.time.DayOfWeek;

import lombok.Data;

@Data
public class AvailableTimesDayOfWeekReqDTO {
	
	private DayOfWeek dayOfWeek;
	private Long userId;

}
