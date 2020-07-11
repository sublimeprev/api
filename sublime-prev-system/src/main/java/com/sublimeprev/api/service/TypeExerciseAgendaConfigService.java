package com.sublimeprev.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sublimeprev.api.model.TypesExercisesAgendasConfig;
import com.sublimeprev.api.repository.TypeExerciseAgendaConfigRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TypeExerciseAgendaConfigService {
	
	private final TypeExerciseAgendaConfigRepository repository;
	
	public void insertTypeExerciseAgendaConfig (Long idTypeExercise, Long idAgendaConfig) {
		TypesExercisesAgendasConfig typeExerciseAgendaConfig = new TypesExercisesAgendasConfig(idTypeExercise, idAgendaConfig);
		repository.save(typeExerciseAgendaConfig);
	}
	
	public void deleteall(Long idAgendaConfig) {
		List<TypesExercisesAgendasConfig> listTypesExercisesAgendasConfig = repository.listTypeExerciseAgendaConfigById(idAgendaConfig);
		if(!listTypesExercisesAgendasConfig.isEmpty()) {
			listTypesExercisesAgendasConfig.forEach(type -> {
				repository.delete(type);
			});
		}
	}
}
