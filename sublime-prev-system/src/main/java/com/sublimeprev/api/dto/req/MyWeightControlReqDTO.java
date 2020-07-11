package com.sublimeprev.api.dto.req;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MyWeightControlReqDTO {

	@NotNull
	private Double value;
}
