package com.sublimeprev.api.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sublimeprev.api.bases.PageReq;
import com.sublimeprev.api.bases.PageRes;
import com.sublimeprev.api.dto.req.EvaluationByUserReqDTO;
import com.sublimeprev.api.dto.res.EvaluationByUserResDTO;
import com.sublimeprev.api.model.Evaluation;
import com.sublimeprev.api.service.EvaluationByUserService;
import com.sublimeprev.api.service.FileStorageService;

import lombok.AllArgsConstructor;

@PreAuthorize("hasAuthority('ADMIN')")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/evaluations-by-user", produces = MediaType.APPLICATION_JSON_VALUE)
public class EvaluationByUserController {

	private final EvaluationByUserService service;
	private final FileStorageService fileStorageService;

	@GetMapping("/{userId}")
	public PageRes<EvaluationByUserResDTO> index(@PathVariable("userId") Long userId, PageReq query) {
		Page<Evaluation> page = this.service.findAll(userId, query);
		return new PageRes<EvaluationByUserResDTO>(
				page.getContent().stream().map(EvaluationByUserResDTO::of).collect(Collectors.toList()),
				page.getTotalElements(), page.getTotalPages());
	}

	@PostMapping("/{userId}")
	public EvaluationByUserResDTO store(@PathVariable("userId") Long userId, @Valid @RequestBody EvaluationByUserReqDTO dto) {
		return EvaluationByUserResDTO
				.of(this.service.save(userId, new Evaluation(), dto.getNewFileName(), dto.getNewFileBase64()));
	}

	@GetMapping("/{userId}/{id}")
	public EvaluationByUserResDTO show(@PathVariable("userId") Long userId, @PathVariable("id") Long id) {
		return EvaluationByUserResDTO.of(this.service.findById(userId, id));
	}

	@PutMapping("/{userId}/{id}")
	public EvaluationByUserResDTO update(@PathVariable("userId") Long userId, @PathVariable("id") Long id,
			@Valid @RequestBody EvaluationByUserReqDTO dto) {
		return EvaluationByUserResDTO.of(this.service.save(userId, this.service.findById(userId, id), dto.getNewFileName(),
				dto.getNewFileBase64()));
	}

	@DeleteMapping("/{userId}/{id}")
	public void logicalExclusion(@PathVariable("userId") Long userId, @PathVariable("id") Long id) {
		this.service.logicalExclusion(userId, id);
	}

	@GetMapping("/{userId}/{id}/file")
	public ResponseEntity<byte[]> getFile(@PathVariable("userId") Long userId, @PathVariable("id") Long id) {
		Evaluation training = this.service.findByIdWithoutContent(userId, id);
		byte[] file = this.fileStorageService.getByKey(training.getFileKey());
		return ResponseEntity.ok().contentLength(file.length).contentType(training.getMediaType()).body(file);
	}
}
