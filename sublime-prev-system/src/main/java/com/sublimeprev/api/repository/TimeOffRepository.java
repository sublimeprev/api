package com.sublimeprev.api.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.sublimeprev.api.bases.BaseRepository;
import com.sublimeprev.api.model.TimeOff;

public interface TimeOffRepository extends BaseRepository<TimeOff, Long> {
	@Query("select count(e)>0 from TimeOff e where date = ?1 and (time=?2 or time is null) and deleted=false")
	boolean existsByDateAndTimeOrTimeIsNull(LocalDate date, LocalTime time);

	Optional<TimeOff> findByDateAndTimeIsNullAndDeletedIsFalse(LocalDate date);

	Optional<TimeOff> findByDateAndTime(LocalDate date, LocalTime time);
}