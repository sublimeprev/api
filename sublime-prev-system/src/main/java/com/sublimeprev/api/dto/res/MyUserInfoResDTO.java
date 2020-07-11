package com.sublimeprev.api.dto.res;

import java.util.List;

import lombok.Data;

@Data
public class MyUserInfoResDTO {

	private Long userId;
	private List<TraininigTypeResDTO> trainingType;
	
	public MyUserInfoResDTO (List<TraininigTypeResDTO> listTraininigTypeResDTO, Long userId) {
		this.trainingType = listTraininigTypeResDTO;
		this.userId = userId;
	}
}
