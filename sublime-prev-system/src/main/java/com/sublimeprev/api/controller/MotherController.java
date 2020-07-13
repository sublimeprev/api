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
import com.sublimeprev.api.dto.req.MotherReqDTO;
import com.sublimeprev.api.dto.res.MotherResDTO;
import com.sublimeprev.api.model.Mother;
import com.sublimeprev.api.service.MotherService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/mothers", produces = MediaType.APPLICATION_JSON_VALUE)
public class MotherController {
	
	private final MotherService service;

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public PageRes<MotherResDTO> index(PageReq query) {
		Page<Mother> page = this.service.findAll(query);
		return new PageRes<MotherResDTO>(page.getContent().stream().map(MotherResDTO::of).collect(Collectors.toList()),
				page.getTotalElements(), page.getTotalPages());
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/all")
	public List<Mother> getAllUsers(){
		return this.service.getAllMothers();
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public MotherResDTO store(@Valid @RequestBody MotherReqDTO dto) {
		Mother mother = this.service.save(dto.toEntity(new Mother()));
		return MotherResDTO.of(mother);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{id}")
	public MotherResDTO show(@PathVariable("id") Long id) {
		return MotherResDTO.of(this.service.findById(id));
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/{id}")
	public MotherResDTO update(@PathVariable("id") Long id, @Valid @RequestBody MotherReqDTO dto) {
		Mother mother = dto.toEntity(this.service.findById(id));
		return MotherResDTO.of(this.service.save(mother));
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}")
	public void logicalExclusion(@PathVariable("id") Long id) {
		this.service.logicalExclusion(id);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/many")
	public List<MotherResDTO> showMany(@RequestParam Long[] ids) {
		return this.service.findByIds(ids).stream().map(MotherResDTO::of).collect(Collectors.toList());
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/restore/{id}")
	public void restoreDeleted(@PathVariable("id") Long id) {
		this.service.restoreDeleted(id);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/destroy/{id}")
	public void permanentDestroy(@PathVariable("id") Long id) {
		this.service.permanentDestroy(id);
	}

}
