package com.sublimeprev.api.controller;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sublimeprev.api.dto.res.TimeItemResDTO;
import com.sublimeprev.api.dto.res.UserAgendaConfigResDTO;
import com.sublimeprev.api.dto.res.UserResDTO;
import com.sublimeprev.api.service.UserAgendaConfigService;

import lombok.AllArgsConstructor;

@PreAuthorize("hasAuthority('ADMIN')")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/user-agenda-configs", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserAgendaConfigController {

	private final UserAgendaConfigService service;

	@GetMapping("/users/{userId}")
	public List<UserAgendaConfigResDTO> findConfigsByUser(@PathVariable("userId") Long userId) {
		return this.service.findConfigsByUser(userId);
	}

	@PutMapping("/users/{userId}")
	public void findConfigsByUser(@PathVariable("userId") Long userId,
			@RequestBody LinkedHashMap<DayOfWeek, LinkedList<TimeItemResDTO>> configs) {
		this.service.saveConfigsByUser(userId, configs);
	}

	@GetMapping("/users-by-agenda/{agendaConfigId}")
	public List<UserResDTO> findUsersByAgendaConfigId(@PathVariable("agendaConfigId") Long agendaConfigId) {
		return this.service.findUsersByAgendaConfigId(agendaConfigId).stream().map(UserResDTO::of)
				.collect(Collectors.toList());
	}

	@PostMapping("/users-by-agenda/{agendaConfigId}")
	public void insertUsersInAgendaConfig(@PathVariable("agendaConfigId") Long agendaConfigId,
			@RequestParam Long[] usersIds) {
		this.service.insertUsersInAgendaConfig(agendaConfigId, Arrays.asList(usersIds));
	}

	@DeleteMapping("/users-by-agenda/{agendaConfigId}")
	public void deleteUsersFromAgendaConfig(@PathVariable("agendaConfigId") Long agendaConfigId,
			@RequestParam Long[] usersIds) {
		this.service.deleteUsersFromAgendaConfig(agendaConfigId, Arrays.asList(usersIds));
	}
}
