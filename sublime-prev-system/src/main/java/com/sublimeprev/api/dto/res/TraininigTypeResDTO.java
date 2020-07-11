package com.sublimeprev.api.dto.res;

import java.util.ArrayList;
import java.util.List;

import com.sublimeprev.api.model.User;

import lombok.Data;

@Data
public class TraininigTypeResDTO {
	
	private Long id;
	private String type;
	
	public TraininigTypeResDTO(Long id, String type) {
		this.id = id;
		this.type = type;
	} 

	public static MyUserInfoResDTO getTraininigTypeResDTO(User entity){
		List<TraininigTypeResDTO> list = new ArrayList<>();
		
		entity.getTypesExercises().forEach(type ->{
			TraininigTypeResDTO traininigTypeResDTO = new TraininigTypeResDTO(type.getId(), type.getType());
			list.add(traininigTypeResDTO);
		});
		MyUserInfoResDTO trainingType = new MyUserInfoResDTO(list, entity.getId()) ;
		return trainingType;
	}
}


