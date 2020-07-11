package com.sublimeprev.api.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sublimeprev.api.dto.req.NotificationReqDTO;
import com.sublimeprev.api.service.NotificationService;

import lombok.AllArgsConstructor;

@PreAuthorize("isAuthenticated()")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationController {

	private final NotificationService service;

	@PostMapping("/send-broadcast")
	public void sendBroadcastNotification(@Valid @RequestBody NotificationReqDTO dto) {
		this.service.sendBroadcastNotification(dto.getTitle(), dto.getContent());
	}

	@PostMapping("/send/{userId}")
	public void sendNotificationToUser(@PathVariable("userId") Long userId,
			@Valid @RequestBody NotificationReqDTO dto) {
		this.service.sendNotificationToUser(userId, dto.getTitle(), dto.getContent());
	}
	
	@PostMapping("/send/all-users-type-exercise/{typeExerciseId}")
	public void sendNotificationAllUsersTypeExercise(@PathVariable("typeExerciseId") Long typeExerciseId,
			@Valid @RequestBody NotificationReqDTO dto) {
		this.service.sendNotificationAllUsersByTypeExercise(typeExerciseId, dto.getTitle(), dto.getContent());
	}
}
