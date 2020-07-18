package com.sublimeprev.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.sublimeprev.api.dto.req.ChildrenReqDTO;
import com.sublimeprev.api.dto.res.ChildrenResDTO;
import com.sublimeprev.api.model.Children;
import com.sublimeprev.api.service.ChildrenService;

@RestController
@RequestMapping(value = "/api/children", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChildrenControler {
	
	@Autowired
	private ChildrenService service;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public ChildrenResDTO store(@RequestBody ChildrenReqDTO dto) {
		Children children = this.service.save(dto.toEntity(new Children()), dto.getIdMother());
		return ChildrenResDTO.of(children);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{id}")
	public ChildrenResDTO show(@PathVariable("id") Long id) {
		return ChildrenResDTO.of(this.service.findById(id));
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/by-mother/{idMother}")
	public ChildrenResDTO findByMother(@PathVariable("idMother") Long id) {
		return ChildrenResDTO.of(this.service.findByMother(id));
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping()
	public ChildrenResDTO update(@RequestBody ChildrenReqDTO dto) {
		Children children = dto.toEntity(this.service.findById(dto.getId()));
		return ChildrenResDTO.of(this.service.save(children, null));
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}")
	public void logicalExclusion(@PathVariable("id") Long id) {
		this.service.logicalExclusion(id);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/many")
	public List<ChildrenResDTO> showMany(@RequestParam Long[] ids) {
		return this.service.findByIds(ids).stream().map(ChildrenResDTO::of).collect(Collectors.toList());
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
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/verify-address/{idMother}")
	public boolean verifyAddressMother(@PathVariable ("idMother") Long idMother) {
		return this.service.verifyAddresMother(idMother);
	}
}
