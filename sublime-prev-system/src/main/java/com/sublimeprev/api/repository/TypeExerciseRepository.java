package com.sublimeprev.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.sublimeprev.api.bases.BaseRepository;
import com.sublimeprev.api.model.TypeExercise;

public interface TypeExerciseRepository extends BaseRepository<TypeExercise, Long>{

	@Query("SELECT t FROM TypeExercise t WHERE t.active = true")
	List<TypeExercise> findAllActive ();
}
