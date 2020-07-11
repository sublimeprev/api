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

import com.sublimeprev.api.bases.PageRes;
import com.sublimeprev.api.dto.req.TimeOffPageReq;
import com.sublimeprev.api.dto.req.TimeOffReqDTO;
import com.sublimeprev.api.dto.res.TimeOffResDTO;
import com.sublimeprev.api.model.TimeOff;
import com.sublimeprev.api.service.TimeOffService;

import lombok.AllArgsConstructor;

@PreAuthorize("hasAuthority('ADMIN')")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/time-offs", produces = MediaType.APPLICATION_JSON_VALUE)
public class TimeOffController {

	private final TimeOffService service;

	@GetMapping
	public PageRes<TimeOffResDTO> index(TimeOffPageReq query) {
		Page<TimeOff> page = this.service.findAll(query);
		return new PageRes<TimeOffResDTO>(
				page.getContent().stream().map(TimeOffResDTO::of).collect(Collectors.toList()), page.getTotalElements(),
				page.getTotalPages());
	}

	@PostMapping
	public TimeOffResDTO store(@Valid @RequestBody TimeOffReqDTO dto) {
		return TimeOffResDTO.of(this.service.save(dto.toEntity(new TimeOff())));
	}

	@GetMapping("/{id}")
	public TimeOffResDTO show(@PathVariable("id") Long id) {
		return TimeOffResDTO.of(this.service.findById(id));
	}

	@PutMapping("/{id}")
	public TimeOffResDTO update(@PathVariable("id") Long id, @Valid @RequestBody TimeOffReqDTO dto) {
		return TimeOffResDTO.of(this.service.save(dto.toEntity(this.service.findById(id))));
	}

	@DeleteMapping("/{id}")
	public void logicalExclusion(@PathVariable("id") Long id) {
		this.service.logicalExclusion(id);
	}

	@GetMapping("/many")
	public List<TimeOffResDTO> showMany(@RequestParam Long[] ids) {
		return this.service.findByIds(ids).stream().map(TimeOffResDTO::of).collect(Collectors.toList());
	}

	@PutMapping("/restore/{id}")
	public void restoreDeleted(@PathVariable("id") Long id) {
		this.service.restoreDeleted(id);
	}

	@DeleteMapping("/destroy/{id}")
	public void permanentDestroy(@PathVariable("id") Long id) {
		this.service.permanentDestroy(id);
	}
}
