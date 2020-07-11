package com.sublimeprev.api.dto.req;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class NotificationReqDTO {

	@NotBlank
	private String title;
	@NotBlank
	private String content;
}
