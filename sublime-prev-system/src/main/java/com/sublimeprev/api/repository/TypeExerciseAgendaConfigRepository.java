package com.sublimeprev.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sublimeprev.api.model.TypesExercisesAgendasConfig;

public interface TypeExerciseAgendaConfigRepository extends JpaRepository<TypesExercisesAgendasConfig, Long>{
	@Query("SELECT t FROM TypesExercisesAgendasConfig t WHERE t.typeExerciseId = ?1 and t.agendaConfigId = ?2")
	public List<TypesExercisesAgendasConfig> listTypeExerciseAgendaConfig(Long typeExerciseId, Long agendaConfigId);
	
	@Query("SELECT t FROM TypesExercisesAgendasConfig t WHERE t.agendaConfigId = ?1")
	public List<TypesExercisesAgendasConfig> listTypeExerciseAgendaConfigById (Long agendaConfigId);
}
