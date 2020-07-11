package com.sublimeprev.api.dto.res;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sublimeprev.api.model.WeightControl;

import lombok.Data;

@Data
public class WeightControlResDTO {

	private Long id;
	private String createdBy;
	private String updatedBy;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Long userId;
//	private String userDesc;
	private LocalDate date;
	private Double value;

	private WeightControlResDTO(WeightControl entity) {
		this.id = entity.getId();
		this.createdBy = entity.getCreatedBy();
		this.updatedBy = entity.getUpdatedBy();
		this.createdAt = entity.getCreatedAt();
		this.updatedAt = entity.getUpdatedAt();
		if (entity.getUser() != null) {
			this.userId = entity.getUser().getId();
//			this.userDesc = entity.getUser().toString();
		}
		this.date = entity.getDate();
		this.value = entity.getValue();
	}

	public static WeightControlResDTO of(WeightControl entity) {
		return entity == null ? null : new WeightControlResDTO(entity);
	}
}
