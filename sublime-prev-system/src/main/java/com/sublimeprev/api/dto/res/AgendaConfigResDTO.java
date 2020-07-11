package com.sublimeprev.api.dto.res;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.sublimeprev.api.model.AgendaConfig;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AgendaConfigResDTO {

	private Long id;
	private String createdBy;
	private String updatedBy;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private DayOfWeek dayOfWeek;
	@JsonFormat(pattern = "HH:mm")
	private LocalTime time;
	private Integer maxLimit;
	private List<TypeExerciseResDTO> listTypeExercise;
	private Long typeExercise;

	private AgendaConfigResDTO(AgendaConfig entity, List<TypeExerciseResDTO> listTypeExercise) {
		this.id = entity.getId();
		this.createdBy = entity.getCreatedBy();
		this.updatedBy = entity.getUpdatedBy();
		this.createdAt = entity.getCreatedAt();
		this.updatedAt = entity.getUpdatedAt();
		this.dayOfWeek = entity.getDayOfWeek();
		this.time = entity.getTime();
		this.maxLimit = entity.getMaxLimit();
		this.listTypeExercise = listTypeExercise;
		if(!listTypeExercise.isEmpty() || listTypeExercise != null) {
			listTypeExercise.forEach(type -> {
				this.typeExercise = type.getId();
				;
			});
		}
	}

	public static AgendaConfigResDTO of(AgendaConfig entity) {
		return entity == null ? null : new AgendaConfigResDTO(entity, listTypeExercise(entity));
	}
	
	private static List<TypeExerciseResDTO> listTypeExercise(AgendaConfig entity){
		List<TypeExerciseResDTO> list = TypeExerciseResDTO.listTypeExerciseResDTO(entity.getTypesExercises());
		return list;
	}
}
