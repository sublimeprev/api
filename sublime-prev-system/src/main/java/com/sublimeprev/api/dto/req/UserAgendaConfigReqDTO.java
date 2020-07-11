package com.sublimeprev.api.dto.req;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserAgendaConfigReqDTO {

	@NotNull
	private Long userId;
	@NotNull
	private Long agendaConfigId;
}
