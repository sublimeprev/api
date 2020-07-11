package com.sublimeprev.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Sort;

import com.sublimeprev.api.bases.BaseRepository;
import com.sublimeprev.api.model.Evaluation;

public interface EvaluationRepository extends BaseRepository<Evaluation, Long> {

	Optional<Evaluation> findFirstByUserIdAndDeletedFalse(Long userId, Sort sort);

	Optional<Evaluation> findByUserIdAndIdAndDeletedFalse(Long userId, Long id);
}
