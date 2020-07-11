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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sublimeprev.api.bases.PageReq;
import com.sublimeprev.api.bases.PageRes;
import com.sublimeprev.api.dto.req.AgendaConfigReqDTO;
import com.sublimeprev.api.dto.res.AgendaConfigResDTO;
import com.sublimeprev.api.dto.res.AvailableDatesAndTimesResDTO;
import com.sublimeprev.api.model.AgendaConfig;
import com.sublimeprev.api.service.AgendaConfigService;
import com.sublimeprev.api.service.SchedulerService;
import com.sublimeprev.api.service.SchedulingService;
import com.sublimeprev.api.service.TypeExerciseAgendaConfigService;

import lombok.AllArgsConstructor;

@PreAuthorize("hasAuthority('ADMIN')")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/agenda-configs", produces = MediaType.APPLICATION_JSON_VALUE)
public class AgendaConfigController {

	private final AgendaConfigService service;
	private final SchedulerService schedulerService;
	private final TypeExerciseAgendaConfigService typeExerciseAgendaConfigService;
	private final SchedulingService schedulingService;

	@GetMapping
	public PageRes<AgendaConfigResDTO> index(PageReq query) {
		Page<AgendaConfig> page = this.service.findAll(query);
		return new PageRes<AgendaConfigResDTO>(
				page.getContent().stream().map(AgendaConfigResDTO::of).collect(Collectors.toList()),
				page.getTotalElements(), page.getTotalPages());
	}

	@PostMapping
	public AgendaConfigResDTO store(@Valid @RequestBody AgendaConfigReqDTO dto) {
		AgendaConfig agendaConfig = this.service.save(dto.toEntity(new AgendaConfig()));
		typeExerciseAgendaConfigService.insertTypeExerciseAgendaConfig(dto.getTypeExercise(), agendaConfig.getId());
		return AgendaConfigResDTO.of(agendaConfig);
	}

	@GetMapping("/{id}")
	public AgendaConfigResDTO show(@PathVariable("id") Long id) {
		return AgendaConfigResDTO.of(this.service.findById(id));
	}
	
	@GetMapping("/availableTimes/{idTypeExercise}")
	public List<AvailableDatesAndTimesResDTO>  getAvailableTimes(@PathVariable("idTypeExercise") Long idTypeExercise){
		return this.schedulingService.availableDatesAndTimes(idTypeExercise);
	}

	@PutMapping("/{id}")
	public AgendaConfigResDTO update(@PathVariable("id") Long id, @Valid @RequestBody AgendaConfigReqDTO dto) {
		typeExerciseAgendaConfigService.deleteall(id);
		AgendaConfig agendaConfig = dto.toEntity(this.service.findById(id));
		typeExerciseAgendaConfigService.insertTypeExerciseAgendaConfig(dto.getTypeExercise(), id);
		return AgendaConfigResDTO.of(this.service.save(agendaConfig));
	}
	
	@DeleteMapping("/{id}")
	public void logicalExclusion(@PathVariable("id") Long id) {
		this.service.logicalExclusion(id);
	}

	@GetMapping("/many")
	public List<AgendaConfigResDTO> showMany(@RequestParam Long[] ids) {
		return this.service.findByIds(ids).stream().map(AgendaConfigResDTO::of).collect(Collectors.toList());
	}

	@PutMapping("/restore/{id}")
	public void restoreDeleted(@PathVariable("id") Long id) {
		this.service.restoreDeleted(id);
	}

	@DeleteMapping("/destroy/{id}")
	public void permanentDestroy(@PathVariable("id") Long id) {
		this.service.permanentDestroy(id);
	}
	
	@GetMapping("/create-schedulings")
	public void createSchedulings() {
		this.schedulerService.createSchedulings();
	}
}
