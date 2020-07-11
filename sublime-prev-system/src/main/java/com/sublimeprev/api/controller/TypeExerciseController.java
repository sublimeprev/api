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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sublimeprev.api.bases.PageReq;
import com.sublimeprev.api.bases.PageRes;
import com.sublimeprev.api.dto.req.TypeExerciseReqDTO;
import com.sublimeprev.api.dto.res.TypeExerciseResDTO;
import com.sublimeprev.api.model.TypeExercise;
import com.sublimeprev.api.service.TypeExerciseService;

import lombok.AllArgsConstructor;

@PreAuthorize("hasAuthority('ADMIN')")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/type-training", produces = MediaType.APPLICATION_JSON_VALUE)
public class TypeExerciseController {
	
	private final TypeExerciseService service;
	
	@GetMapping
	public PageRes<TypeExerciseResDTO> index(PageReq query) {
		Page<TypeExercise> page = this.service.findAllPage(query);
		return new PageRes<TypeExerciseResDTO>(page.getContent().stream().map(TypeExerciseResDTO::toEntity).collect(Collectors.toList()),
				page.getTotalElements(), page.getTotalPages());
	}
	
	@GetMapping("/many")
	public List<TypeExerciseResDTO> showMany(@RequestParam Long[] ids) {
		return this.service.findByIds(ids).stream().map(TypeExerciseResDTO::toEntity).collect(Collectors.toList());
	}
	
	@PostMapping
	public void store(@Valid @RequestBody TypeExerciseReqDTO dto) {
		this.service.save(dto.toEntity(new TypeExercise()));
	}
	
	@GetMapping("/all")
	public List<TypeExerciseResDTO> findAll(){
		List<TypeExercise> lisDto = this.service.findAllActive();
    	return TypeExerciseResDTO.listTypeExerciseResDTO(lisDto);
    }
	
	@DeleteMapping("/{id}")
	public void deleteLogical(@PathVariable Long id){
		
    	service.deleteLogical(id);
    }
}
