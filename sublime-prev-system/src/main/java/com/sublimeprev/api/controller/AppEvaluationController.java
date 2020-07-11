package com.sublimeprev.api.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sublimeprev.api.service.AppEvaluationService;

import lombok.AllArgsConstructor;

@PreAuthorize("isAuthenticated()")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/app-evaluations", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppEvaluationController {

	private final AppEvaluationService appEvaluationService;

	@GetMapping("/download-last")
	public ResponseEntity<byte[]> myLastEvaluation() {
		return this.appEvaluationService.myLastEvaluation();
	}

	@GetMapping("/download-last-url")
	public String myLastEvaluationUrl() {
		return this.appEvaluationService.myLastEvaluationUrl();
	}

}
