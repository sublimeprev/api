package com.sublimeprev.api.dto.res;

import java.time.LocalDate;

import com.sublimeprev.api.model.WeightControl;

import lombok.Data;

@Data
public class MyWeightControlResDTO {

	private Long id;
	private LocalDate date;
	private Double value;

	private MyWeightControlResDTO(WeightControl entity) {
		this.id = entity.getId();
		this.date = entity.getDate();
		this.value = entity.getValue();
	}

	public static MyWeightControlResDTO of(WeightControl entity) {
		return entity == null ? null : new MyWeightControlResDTO(entity);
	}
}
