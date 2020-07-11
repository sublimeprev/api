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
import com.sublimeprev.api.config.security.AuthUtil;
import com.sublimeprev.api.dto.req.UserReqDTO;
import com.sublimeprev.api.dto.res.UserAppResDTO;
import com.sublimeprev.api.dto.res.UserResDTO;
import com.sublimeprev.api.model.User;
import com.sublimeprev.api.service.TypeExerciseUserService;
import com.sublimeprev.api.service.UserService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	private final UserService service;
	private final TypeExerciseUserService typeExerciseUserService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public PageRes<UserResDTO> index(PageReq query) {
		Page<User> page = this.service.findAll(query);
		return new PageRes<UserResDTO>(page.getContent().stream().map(UserResDTO::of).collect(Collectors.toList()),
				page.getTotalElements(), page.getTotalPages());
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/all")
	public List<User> getAllUsers(){
		return this.service.getAllUsers();
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public UserResDTO store(@Valid @RequestBody UserReqDTO dto) {
		User user = this.service.save(dto.toEntity(new User()));
		dto.getTypeExercise().forEach(idType -> {
			typeExerciseUserService.insertTyoeExerciseUser(idType, user.getId());
			});
		return UserResDTO.of(user);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{id}")
	public UserResDTO show(@PathVariable("id") Long id) {
		Long idUser = null;
		if(id == null) {
			idUser = AuthUtil.getUserId();
		}else {
			idUser = id;
		}
		return UserResDTO.of(this.service.findById(idUser));
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/{id}")
	public UserResDTO update(@PathVariable("id") Long id, @Valid @RequestBody UserReqDTO dto) {
		Long idUser = null;
		if(id == null) {
			idUser = AuthUtil.getUserId();
		}else {
			idUser = id;
		}
		User user = dto.toEntity(this.service.findById(idUser));
		if(dto.getTypeExercise() != null) {
			typeExerciseUserService.deleteall(idUser);
			
			dto.getTypeExercise().forEach(idType -> {
				typeExerciseUserService.insertTyoeExerciseUser(idType, user.getId());
				});
			}
		
		return UserResDTO.of(this.service.save(user));
	}
	
	@PutMapping("/profile")
	public UserAppResDTO updateProfile(@Valid @RequestBody UserReqDTO dto) {
		
		Long idUser = AuthUtil.getUserId();
		User user = dto.toEntityProfile(this.service.findById(idUser));
		return UserAppResDTO.of(this.service.saveProfile(user));
	}

	@GetMapping("/profile")
	public UserAppResDTO findByLoogedUser() {
		Long idUser = AuthUtil.getUserId();
		User user = this.service.findById(idUser);
		return UserAppResDTO.of(user);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}")
	public void logicalExclusion(@PathVariable("id") Long id) {
		this.service.logicalExclusion(id);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/many")
	public List<UserResDTO> showMany(@RequestParam Long[] ids) {
		return this.service.findByIds(ids).stream().map(UserResDTO::of).collect(Collectors.toList());
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
