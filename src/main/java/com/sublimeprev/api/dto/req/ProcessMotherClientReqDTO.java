package com.sublimeprev.api.dto.req;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ProcessMotherClientReqDTO {
	
	private String cpf;
	private LocalDate birthDay;

}
