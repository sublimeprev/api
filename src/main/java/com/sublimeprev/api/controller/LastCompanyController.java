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

import com.sublimeprev.api.dto.req.LastCompanyReqDTO;
import com.sublimeprev.api.dto.res.LastCompanyResDTO;
import com.sublimeprev.api.model.LastCompany;
import com.sublimeprev.api.service.LastCompanyService;

@RestController
@RequestMapping(value = "/api/last-company", produces = MediaType.APPLICATION_JSON_VALUE)
public class LastCompanyController {
	
	@Autowired
	private LastCompanyService service;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public LastCompanyResDTO store(@RequestBody LastCompanyReqDTO dto) {
		LastCompany lastCompany = this.service.save(dto.toEntity(new LastCompany()), dto.getIdMother());
		return LastCompanyResDTO.of(lastCompany);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{id}")
	public LastCompanyResDTO show(@PathVariable("id") Long id) {
		return LastCompanyResDTO.of(this.service.findById(id));
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/by-mother/{idMother}")
	public LastCompanyResDTO findByMother(@PathVariable("idMother") Long id) {
		return LastCompanyResDTO.of(this.service.findByMother(id));
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping()
	public LastCompanyResDTO update(@RequestBody LastCompanyReqDTO dto) {
		LastCompany lastCompany = dto.toEntity(this.service.findById(dto.getId()));
		return LastCompanyResDTO.of(this.service.save(lastCompany, null));
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}")
	public void logicalExclusion(@PathVariable("id") Long id) {
		this.service.logicalExclusion(id);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/many")
	public List<LastCompanyResDTO> showMany(@RequestParam Long[] ids) {
		return this.service.findByIds(ids).stream().map(LastCompanyResDTO::of).collect(Collectors.toList());
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
