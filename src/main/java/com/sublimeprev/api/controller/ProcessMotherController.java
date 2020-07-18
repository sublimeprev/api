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

import com.sublimeprev.api.dto.req.ProcessMotherReqDTO;
import com.sublimeprev.api.dto.res.ProcessMotherClientResDTO;
import com.sublimeprev.api.dto.res.ProcessMotherResDTO;
import com.sublimeprev.api.model.ProcessMother;
import com.sublimeprev.api.service.ProcessMotherService;

@RestController
@RequestMapping(value = "/api/process-mothers", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProcessMotherController {
	
	@Autowired
	private ProcessMotherService service;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public ProcessMotherResDTO store(@RequestBody ProcessMotherReqDTO dto) {
		ProcessMother processMother = this.service.save(dto.toEntity(new ProcessMother()), dto.getIdMother());
		return ProcessMotherResDTO.of(processMother);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{id}")
	public ProcessMotherResDTO show(@PathVariable("id") Long id) {
		return ProcessMotherResDTO.of(this.service.findById(id));
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/by-mother/{idMother}")
	public ProcessMotherResDTO findByMother(@PathVariable("idMother") Long id) {
		return ProcessMotherResDTO.of(this.service.findByMother(id));
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping()
	public ProcessMotherResDTO update(@RequestBody ProcessMotherReqDTO dto) {
		ProcessMother processMother = dto.toEntity(this.service.findById(dto.getId()));
		return ProcessMotherResDTO.of(this.service.save(processMother, null));
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}")
	public void logicalExclusion(@PathVariable("id") Long id) {
		this.service.logicalExclusion(id);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/many")
	public List<ProcessMotherResDTO> showMany(@RequestParam Long[] ids) {
		return this.service.findByIds(ids).stream().map(ProcessMotherResDTO::of).collect(Collectors.toList());
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
	
	@GetMapping("/mother-client/{cpf}/{birthday}")
	public ProcessMotherClientResDTO findProcessMotherClient(@PathVariable ("cpf") String cpf, @PathVariable String birthday) {
		ProcessMother  processMother = this.service.findByCpfAndBirthdayMother(cpf, birthday);
		return ProcessMotherClientResDTO.of(processMother);
	}

}
