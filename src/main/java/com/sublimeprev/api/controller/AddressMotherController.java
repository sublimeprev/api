package com.sublimeprev.api.controller;

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

import com.sublimeprev.api.dto.req.AddressMotherReqDTO;
import com.sublimeprev.api.dto.res.AddressMotherResDTO;
import com.sublimeprev.api.model.AddressMother;
import com.sublimeprev.api.service.AddressMotherService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/address-mothers", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressMotherController {
	
	private final AddressMotherService service;

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public AddressMotherResDTO store(@RequestBody AddressMotherReqDTO dto) {
		AddressMother addressMother = this.service.save(dto.toEntity(dto), dto.getIdMother());
		return AddressMotherResDTO.of(addressMother);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{id}")
	public AddressMotherResDTO show(@PathVariable("id") Long id) {
		return AddressMotherResDTO.of(this.service.findById(id));
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping()
	public AddressMotherResDTO update(@RequestBody AddressMotherReqDTO dto) {
		AddressMother addressMother = dto.toEntity(dto);
		return AddressMotherResDTO.of(this.service.save(addressMother, null));
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}")
	public void logicalExclusion(@PathVariable("id") Long id) {
		this.service.logicalExclusion(id);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/many")
	public List<AddressMotherResDTO> showMany(@RequestParam Long[] ids) {
		return this.service.findByIds(ids).stream().map(AddressMotherResDTO::of).collect(Collectors.toList());
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
