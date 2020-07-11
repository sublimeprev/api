package com.sublimeprev.api.controller;

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

import com.sublimeprev.api.bases.PageRes;
import com.sublimeprev.api.dto.req.MyWeightControlReqDTO;
import com.sublimeprev.api.dto.req.WeightControlPageReq;
import com.sublimeprev.api.dto.res.MyWeightControlResDTO;
import com.sublimeprev.api.model.WeightControl;
import com.sublimeprev.api.service.MyWeightControlsService;

import lombok.AllArgsConstructor;

@PreAuthorize("isAuthenticated()")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/app-weight-controls", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppWeightControlController {

	private final MyWeightControlsService service;

	@GetMapping
	public PageRes<MyWeightControlResDTO> index(WeightControlPageReq query) {
		Page<WeightControl> page = this.service.findAllMine(query);
		return new PageRes<MyWeightControlResDTO>(
				page.getContent().stream().map(MyWeightControlResDTO::of).collect(Collectors.toList()),
				page.getTotalElements(), page.getTotalPages());
	}

	@PostMapping
	public MyWeightControlResDTO store(@Valid @RequestBody MyWeightControlReqDTO dto) {
		return MyWeightControlResDTO.of(this.service.insert(dto.getValue()));
	}
	
	@DeleteMapping("/{id}")
	public void logicalExclusion(@PathVariable("id") Long id) {
		this.service.logicalExclusion(id);
	}
}
