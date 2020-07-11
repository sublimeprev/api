package com.sublimeprev.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sublimeprev.api.dto.res.MyUserInfoResDTO;
import com.sublimeprev.api.dto.res.TraininigTypeResDTO;
import com.sublimeprev.api.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/app-user-info", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppUserInfoController {
	
	private final UserService service;

	@GetMapping()
	public MyUserInfoResDTO getTrainingType (){
		return TraininigTypeResDTO.getTraininigTypeResDTO(service.findAuthenticatedUser());
	}
}
