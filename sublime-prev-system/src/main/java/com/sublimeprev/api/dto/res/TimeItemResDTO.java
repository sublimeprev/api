package com.sublimeprev.api.dto.res;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.sublimeprev.api.model.AgendaConfig;
import com.sublimeprev.api.model.TypeExercise;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeItemResDTO {

	private Long agendaConfigId;
	@JsonFormat(pattern = "HH:mm")
	private LocalTime time;
	private boolean checked;
	private boolean hasTrainingAvailable;
	private List<TypeExerciseResDTO> listTypeExercise;
	private Long typeExerciseId;
	
	public TimeItemResDTO(AgendaConfig config, List<Long> agendasIdsOfUser) {
		this.agendaConfigId = config.getId();
		this.time = config.getTime();
		this.checked = agendasIdsOfUser.contains(config.getId());
	}
	
	public TimeItemResDTO(AgendaConfig config, List<Long> agendasIdsOfUser, List<TypeExercise> listTypeExercise) {
		this.agendaConfigId = config.getId();
		this.time = config.getTime();
		this.checked = agendasIdsOfUser.contains(config.getId());
		this.listTypeExercise = TypeExerciseResDTO.listTypeExerciseResDTO(config.getTypesExercises());
		this.hasTrainingAvailable = veririfyHasTrainingAvailable(listTypeExercise, TypeExerciseResDTO.listTypeExerciseResDTO(config.getTypesExercises()));
	}
	
	private boolean veririfyHasTrainingAvailable(List<TypeExercise> listTypeExercise, List<TypeExerciseResDTO> listTypeExerciseResDTO) {
		List<Long> listLongResDTO = new ArrayList<>();
		List<Long> listLong = new ArrayList<>();
		
		listTypeExercise.forEach(type -> {
			listLongResDTO.add(type.getId());
		});
		
		listTypeExerciseResDTO.forEach(typeResDTO -> {
			if(listLongResDTO.contains(typeResDTO.getId())) {
				listLong.add(typeResDTO.getId());
			}
		});
		
		if(listLong.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
}
