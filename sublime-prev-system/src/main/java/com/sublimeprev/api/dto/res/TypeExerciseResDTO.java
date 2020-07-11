package com.sublimeprev.api.dto.res;

import java.util.ArrayList;
import java.util.List;

import com.sublimeprev.api.model.TypeExercise;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeExerciseResDTO {

	private Long id;
	private String type;
	
	private TypeExerciseResDTO(TypeExercise entity) {
		this.id = entity.getId();
		this.type = entity.getType();
	}
	
	public static TypeExerciseResDTO toEntity(TypeExercise entity) {
		TypeExerciseResDTO dto = new TypeExerciseResDTO(entity);
		return dto;
	}
	
	public static List<TypeExerciseResDTO> listTypeExerciseResDTO(List<TypeExercise> list){
		List<TypeExerciseResDTO> listDto = new ArrayList<>();
		
		list.forEach(typeExercise -> {
			
			listDto.add(new TypeExerciseResDTO(typeExercise));
		});
		return listDto;
	}
}
