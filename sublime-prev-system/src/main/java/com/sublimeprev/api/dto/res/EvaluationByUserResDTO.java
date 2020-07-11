package com.sublimeprev.api.dto.res;

import java.time.LocalDateTime;

import com.sublimeprev.api.model.Evaluation;

import lombok.Data;

@Data
public class EvaluationByUserResDTO {

	private Long id;
	private String createdBy;
	private String updatedBy;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Long userId;
	private String userDesc;
	private String fileKey;
	private long size;
	private String mimetype;
	private String encodedContent;

	private EvaluationByUserResDTO(Evaluation entity) {
		this.id = entity.getId();
		this.createdBy = entity.getCreatedBy();
		this.updatedBy = entity.getUpdatedBy();
		this.createdAt = entity.getCreatedAt();
		this.updatedAt = entity.getUpdatedAt();
		if (entity.getUser() != null) {
			this.userId = entity.getUser().getId();
			this.userDesc = entity.getUser().toString();
		}
		this.fileKey = entity.getFileKey();
		this.size = entity.getSize();
		this.mimetype = entity.getMimetype();
		this.encodedContent = entity.getEncodedContent();
	}

	public static EvaluationByUserResDTO of(Evaluation entity) {
		return entity == null ? null : new EvaluationByUserResDTO(entity);
	}
}
