package com.sublimeprev.api.dto.req;

import javax.validation.constraints.NotNull;

import com.sublimeprev.api.model.User;
import com.sublimeprev.api.model.WeightControl;

import java.time.LocalDate;

import lombok.Data;

@Data
public class WeightControlReqDTO {

	@NotNull
	private Long userId;
	@NotNull
	private LocalDate date;
	@NotNull
	private Double value;

	public WeightControl toEntity(WeightControl entity) {
		entity.setUser(this.userId == null ? null : User.builder().id(this.userId).build());
		entity.setDate(this.date);
		entity.setValue(this.value);
		return entity;
	}
}
