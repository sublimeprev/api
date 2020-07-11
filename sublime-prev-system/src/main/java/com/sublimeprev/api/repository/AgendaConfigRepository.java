package com.sublimeprev.api.repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;

import com.sublimeprev.api.bases.BaseRepository;
import com.sublimeprev.api.model.AgendaConfig;
import com.sublimeprev.api.model.TypeExercise;

public interface AgendaConfigRepository extends BaseRepository<AgendaConfig, Long> {

	Optional<AgendaConfig> findFirstByDayOfWeekAndTimeAndDeletedIsFalse(DayOfWeek dayOfWeek, LocalTime time);

	List<AgendaConfig> findByDayOfWeekAndDeletedIsFalse(DayOfWeek dayOfWeek, Sort sort);
	
	List<AgendaConfig> findByDayOfWeekAndTypesExercisesAndDeletedIsFalseOrderByTimeAsc(DayOfWeek dayOfWeek, TypeExercise typesExercise);
}
