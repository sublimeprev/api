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
import com.sublimeprev.api.dto.req.TrainingByUserReqDTO;
import com.sublimeprev.api.dto.res.TrainingByUserResDTO;
import com.sublimeprev.api.model.Training;
import com.sublimeprev.api.service.FileStorageService;
import com.sublimeprev.api.service.TrainingByUserService;

import lombok.AllArgsConstructor;

@PreAuthorize("hasAuthority('ADMIN')")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/trainings-by-user", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrainingByUserController {

	private final TrainingByUserService service;
	private final FileStorageService fileStorageService;

	@GetMapping("/{userId}")
	public PageRes<TrainingByUserResDTO> index(@PathVariable("userId") Long userId, PageReq query) {
		Page<Training> page = this.service.findAll(userId, query);
		return new PageRes<TrainingByUserResDTO>(
				page.getContent().stream().map(TrainingByUserResDTO::of).collect(Collectors.toList()),
				page.getTotalElements(), page.getTotalPages());
	}

	@PostMapping("/{userId}")
	public TrainingByUserResDTO store(@PathVariable("userId") Long userId, @Valid @RequestBody TrainingByUserReqDTO dto) {
		return TrainingByUserResDTO
				.of(this.service.save(userId, new Training(), dto.getNewFileName(), dto.getNewFileBase64()));
	}

	@GetMapping("/{userId}/{id}")
	public TrainingByUserResDTO show(@PathVariable("userId") Long userId, @PathVariable("id") Long id) {
		return TrainingByUserResDTO.of(this.service.findById(userId, id));
	}

	@PutMapping("/{userId}/{id}")
	public TrainingByUserResDTO update(@PathVariable("userId") Long userId, @PathVariable("id") Long id,
			@Valid @RequestBody TrainingByUserReqDTO dto) {
		return TrainingByUserResDTO.of(this.service.save(userId, this.service.findById(userId, id), dto.getNewFileName(),
				dto.getNewFileBase64()));
	}

	@DeleteMapping("/{userId}/{id}")
	public void logicalExclusion(@PathVariable("userId") Long userId, @PathVariable("id") Long id) {
		this.service.logicalExclusion(userId, id);
	}

	@GetMapping("/{userId}/{id}/file")
	public ResponseEntity<byte[]> getFile(@PathVariable("userId") Long userId, @PathVariable("id") Long id) {
		Training training = this.service.findByIdWithoutContent(userId, id);
		byte[] file = this.fileStorageService.getByKey(training.getFileKey());
		return ResponseEntity.ok().contentLength(file.length).contentType(training.getMediaType()).body(file);
	}
}
