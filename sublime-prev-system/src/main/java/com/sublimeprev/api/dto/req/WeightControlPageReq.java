package com.sublimeprev.api.dto.req;

import com.sublimeprev.api.bases.PageReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeightControlPageReq extends PageReq {

	private Integer year;
	private Integer month;
}
