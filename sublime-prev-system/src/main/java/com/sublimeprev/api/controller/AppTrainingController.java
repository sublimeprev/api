package com.sublimeprev.api.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sublimeprev.api.service.AppTrainingService;

import lombok.AllArgsConstructor;
	
@PreAuthorize("isAuthenticated()")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/app-trainings", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppTrainingController {

	private final AppTrainingService service;
	
	@GetMapping("/download-last")
	public ResponseEntity<byte[]> myLastTraining() {
		return this.service.myLastTraining();	
	}
	
	@GetMapping("/download-last-url")
	public String myLastTrainingUrl() {
		return this.service.myLastTrainingUrl();	
	}	
}
