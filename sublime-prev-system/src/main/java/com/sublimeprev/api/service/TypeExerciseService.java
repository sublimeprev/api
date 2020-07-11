package com.sublimeprev.api.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sublimeprev.api.bases.PageReq;
import com.sublimeprev.api.config.i18n.Messages;
import com.sublimeprev.api.config.i18n.ServiceException;
import com.sublimeprev.api.model.TypeExercise;
import com.sublimeprev.api.repository.TypeExerciseRepository;
import com.sublimeprev.api.util.SearchUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TypeExerciseService {
	
	private final TypeExerciseRepository  repository;
	
	public Page<TypeExercise> findAllPage(PageReq query) {
		Specification<TypeExercise> deleted = SearchUtils.specByDeleted(query.isDeleted());
		Specification<TypeExercise> filters = SearchUtils.specByFilter(query.getFilter(), "type", "id",  "active");
		return this.repository.findAll(deleted.and(filters), query.toPageRequest());
	}
	
	public TypeExercise findById(Long id) {
		return this.repository.findById(id).orElseThrow(() -> new ServiceException(Messages.record_not_found));
	}
	
	public TypeExercise save(TypeExercise pojo) {
		pojo.setActive(true);
		return this.findById(this.repository.save(pojo).getId());
	}
	
	public List<TypeExercise> findAll() {
		return this.repository.findAll();
	}
	
	public List<TypeExercise> findAllActive() {
		return this.repository.findAllActive();
	}
	
	public List<TypeExercise> findByIds(Long[] ids) {
		return this.repository.findAllById(Arrays.asList(ids));
	}
	
	public void deleteLogical(Long id) {
		TypeExercise typeExercise = findById(id);
		
		typeExercise.setActive(false);
		
		repository.save(typeExercise);
	}

}
