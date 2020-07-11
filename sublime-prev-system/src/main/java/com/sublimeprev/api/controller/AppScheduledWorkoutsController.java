package com.sublimeprev.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sublimeprev.api.bases.PageReq;
import com.sublimeprev.api.bases.PageRes;
import com.sublimeprev.api.dto.req.MyScheduledWorkoutReqDTO;
import com.sublimeprev.api.dto.res.AvailableDatesAndTimesResDTO;
import com.sublimeprev.api.dto.res.MyScheduledWorkoutResDTO;
import com.sublimeprev.api.model.Scheduling;
import com.sublimeprev.api.service.MyScheduledWorkoutsService;

import lombok.AllArgsConstructor;

@PreAuthorize("isAuthenticated()")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/app-scheduled-workouts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppScheduledWorkoutsController {

	private final MyScheduledWorkoutsService service;

	@GetMapping
	public PageRes<MyScheduledWorkoutResDTO> index(PageReq query) {
		Page<Scheduling> page = this.service.findAllMine(query);
		return new PageRes<MyScheduledWorkoutResDTO>(
				page.getContent().stream().map(MyScheduledWorkoutResDTO::of).collect(Collectors.toList()),
				page.getTotalElements(), page.getTotalPages());
	}

	@PostMapping
	public MyScheduledWorkoutResDTO store(@Valid @RequestBody MyScheduledWorkoutReqDTO dto) {
		return MyScheduledWorkoutResDTO.of(this.service.createNewScheduling(dto.getDate(), dto.getTime(), dto.getIdTypeTraining()));
	}

	@DeleteMapping("/{id}")
	public MyScheduledWorkoutResDTO cancel(@PathVariable("id") Long id) {
		return MyScheduledWorkoutResDTO.of(this.service.cancel(id));
	}

	@GetMapping("/available-dates-and-times/{id}")
	public List<AvailableDatesAndTimesResDTO> availableDatesAndTimes(@PathVariable("id") Long idTypeExecise  ) {
		return this.service.availableDatesAndTimes(idTypeExecise);
	}
}
