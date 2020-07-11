package com.sublimeprev.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sublimeprev.api.model.TypesExerciseUser;
import com.sublimeprev.api.repository.TypeExerciseUserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TypeExerciseUserService {

	private final TypeExerciseUserRepository repository;
	
	public void insertTyoeExerciseUser(Long typeExerciseId, Long userId) {
		TypesExerciseUser typeExerciseuser = new TypesExerciseUser(typeExerciseId, userId);
		repository.save(typeExerciseuser);
	}
	
	public void deleteall(Long idAgendaConfig) {
		List<TypesExerciseUser> listTypesExercisesUser = repository.listTypeExerciseUserId(idAgendaConfig);
		if(!listTypesExercisesUser.isEmpty()) {
			listTypesExercisesUser.forEach(type -> {
				repository.delete(type);
			});
		}
	}
	
	public List<Long> findAllUserIdsByTypeExerciseId(Long typeExerciseId){
		return repository.listUserIdByTypeExercise(typeExerciseId);
	}
	
	public List<Long> findAllTypeExerciseIdsByUserId(Long userId){
		return repository.listTypeExerciseIdByUserId(userId);
	}
}
