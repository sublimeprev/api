package com.sublimeprev.api.dto.res;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAgendaConfigResDTO {
	
	private String typeExercise;
	private LinkedHashMap<DayOfWeek, LinkedList<TimeItemResDTO>> configs;
	

	public static List<UserAgendaConfigResDTO> listUserAgendaConfigResDTO(LinkedHashMap<DayOfWeek, LinkedList<TimeItemResDTO>> configs, List<TypeExerciseResDTO> typeExerciseUser){
		List<UserAgendaConfigResDTO> list = new ArrayList<>();
//		LinkedHashMap<DayOfWeek, LinkedList<TimeItemResDTO>> newConfigs = new LinkedHashMap<DayOfWeek, LinkedList<TimeItemResDTO>>();
		
		typeExerciseUser.forEach(type -> {
			
			UserAgendaConfigResDTO userAgendaConfigResDTO = new UserAgendaConfigResDTO(type.getType(), null);
			LinkedHashMap<DayOfWeek, LinkedList<TimeItemResDTO>> newConfigs = new LinkedHashMap<DayOfWeek, LinkedList<TimeItemResDTO>>();
			for (Map.Entry<DayOfWeek, LinkedList<TimeItemResDTO>> config : configs.entrySet()) {
				
				LinkedList<TimeItemResDTO> listTimeResDTO = new LinkedList<TimeItemResDTO>();
				
				
				config.getValue().forEach(timeItemResDTO -> {
					
					if(timeItemResDTO.getListTypeExercise().contains(type)) {
						listTimeResDTO.add(timeItemResDTO);
					}
					
				});
			
				newConfigs.put(config.getKey(), listTimeResDTO);
				
				
			}	
			userAgendaConfigResDTO.setConfigs(newConfigs);
			list.add(userAgendaConfigResDTO);
		});
		
		return list;
	}
}
