package com.sublimeprev.api.dto.res;

import lombok.Data;

@Data
public class UserTypeExerciseTodayResDTO {
	private String name;
	private String typeExercise;
	
	public UserTypeExerciseTodayResDTO (String name, String typeExercise) {
		this.name = name;
		this.typeExercise = typeExercise;
	}
	
}
