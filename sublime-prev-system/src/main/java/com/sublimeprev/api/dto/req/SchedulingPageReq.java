package com.sublimeprev.api.dto.req;

import com.sublimeprev.api.bases.PageReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchedulingPageReq extends PageReq {

	private String user;
	private String date;
	private String time;
}
