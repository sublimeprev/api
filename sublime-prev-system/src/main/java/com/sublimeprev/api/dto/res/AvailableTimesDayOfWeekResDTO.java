package com.sublimeprev.api.dto.res;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.sublimeprev.api.model.AgendaConfig;

import lombok.Data;

@Data
public class AvailableTimesDayOfWeekResDTO {
	@NotNull
	private DayOfWeek dayOfWeek;
	@NotNull
	private LocalTime time;	
	
	public static List<AvailableTimesDayOfWeekResDTO> createListAvailableTimesDayOfWeekResDTO (List<AgendaConfig> listAgendaConfig) {
		List<AvailableTimesDayOfWeekResDTO> listAvailableTimesDayOfWeekResDTO = new ArrayList<>();
		listAgendaConfig.forEach(agendaConfig -> {
			AvailableTimesDayOfWeekResDTO availableTimesDayOfWeekResDTO = new AvailableTimesDayOfWeekResDTO();
			availableTimesDayOfWeekResDTO.setDayOfWeek(agendaConfig.getDayOfWeek());
			availableTimesDayOfWeekResDTO.setTime(agendaConfig.getTime());
			
			listAvailableTimesDayOfWeekResDTO.add(availableTimesDayOfWeekResDTO);
			
		});
		return listAvailableTimesDayOfWeekResDTO;
	}
}
