package com.sublimeprev.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sublimeprev.api.model.TypeExercise;
import com.sublimeprev.api.model.TypesExerciseUser;

public interface TypeExerciseUserRepository extends JpaRepository<TypesExerciseUser, Long>{
	@Query("SELECT t FROM TypesExerciseUser t WHERE t.typeExerciseId = ?1 and t.userId = ?2")
	public List<TypesExerciseUser> listTypeExerciseUser(Long typeExerciseId, Long userId);
	
	@Query("SELECT t FROM TypesExerciseUser t WHERE t.userId = ?1")
	public List<TypesExerciseUser> listTypeExerciseUserId (Long userId);
	
	@Query("SELECT t.userId FROM TypesExerciseUser t WHERE t.typeExerciseId = ?1 AND user.deleted = false")
	public List<Long> listUserIdByTypeExercise (Long typeExerciseId);
	
	@Query("SELECT t.typeExerciseId FROM TypesExerciseUser t WHERE t.userId = ?1 AND user.deleted = false")
	public List<Long> listTypeExerciseIdByUserId (Long userId);
	
	@Query("SELECT te FROM TypesExerciseUser teu JOIN TypeExercise te ON te.id = teu.typeExerciseId WHERE teu.userId = ?1")
	List<TypeExercise> findTypeExerciseByUserId(Long userId);
	
}
