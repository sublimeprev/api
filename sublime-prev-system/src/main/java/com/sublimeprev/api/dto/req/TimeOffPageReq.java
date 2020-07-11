package com.sublimeprev.api.dto.req;

import com.sublimeprev.api.bases.PageReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeOffPageReq extends PageReq {
	
	private String date;
	private String time;
}
