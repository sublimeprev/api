package com.sublimeprev.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Sort;

import com.sublimeprev.api.bases.BaseRepository;
import com.sublimeprev.api.model.Training;

public interface TrainingRepository extends BaseRepository<Training, Long> {

	Optional<Training> findFirstByUserIdAndDeletedFalse(Long userId, Sort sort);

	Optional<Training> findByUserIdAndIdAndDeletedFalse(Long userId, Long id);
}
