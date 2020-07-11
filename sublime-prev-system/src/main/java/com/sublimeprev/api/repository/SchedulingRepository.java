package com.sublimeprev.api.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.sublimeprev.api.bases.BaseRepository;
import com.sublimeprev.api.domain.SchedulingStatus;
import com.sublimeprev.api.model.Scheduling;
import com.sublimeprev.api.model.User;

public interface SchedulingRepository extends BaseRepository<Scheduling, Long> {

	Optional<Scheduling> findByIdAndUserId(Long id, Long userId);
	
	@Query("SELECT s FROM Scheduling s WHERE s.user = ?1")
	List<Scheduling> findAllUser(User user);

	long countByDateAndTimeAndStatus(LocalDate date, LocalTime time, SchedulingStatus status);

	long countByDateAndTimeAndStatusAndIdNot(LocalDate date, LocalTime time, SchedulingStatus marcado, Long id);

	Optional<Scheduling> findFirstByDateAndTimeAndUserId(LocalDate date, LocalTime time, Long userId);
	
	@Query("SELECT s FROM Scheduling s WHERE s.date = ?1 AND s.status = 1 AND  s.deleted = false ORDER BY s.time")
	List<Scheduling> findAllByDate (LocalDate today);
}
