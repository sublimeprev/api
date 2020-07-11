package com.sublimeprev.api.controller;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sublimeprev.api.bases.PageRes;
import com.sublimeprev.api.dto.req.WeightControlPageReq;
import com.sublimeprev.api.dto.res.WeightControlResDTO;
import com.sublimeprev.api.model.WeightControl;
import com.sublimeprev.api.service.WeightControlByUserService;

import lombok.AllArgsConstructor;

@PreAuthorize("hasAuthority('ADMIN')")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/weight-controls-by-user", produces = MediaType.APPLICATION_JSON_VALUE)
public class WeightControlController {

	private final WeightControlByUserService service;

	@GetMapping("/{userId}")
	public PageRes<WeightControlResDTO> index(@PathVariable("userId") Long userId, WeightControlPageReq query) {
		Page<WeightControl> page = this.service.findAll(userId, query);
		return new PageRes<WeightControlResDTO>(
				page.getContent().stream().map(WeightControlResDTO::of).collect(Collectors.toList()),
				page.getTotalElements(), page.getTotalPages());
	}
}
