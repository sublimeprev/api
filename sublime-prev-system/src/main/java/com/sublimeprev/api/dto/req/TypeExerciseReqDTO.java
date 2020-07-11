package com.sublimeprev.api.dto.req;

import com.sublimeprev.api.model.TypeExercise;

import lombok.Data;

@Data
public class TypeExerciseReqDTO {
	
	private String type;
	
	public TypeExercise toEntity(TypeExercise entity) {
		
		entity.setType(this.type);
		
		return entity;
	}
}
