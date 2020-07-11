package com.sublimeprev.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.sublimeprev.api.bases.BaseRepository;
import com.sublimeprev.api.model.WeightControl;

public interface WeightControlRepository extends BaseRepository<WeightControl, Long> {

	Page<WeightControl> findPageByUserId(Long userId, Specification<WeightControl> spec, PageRequest pageRequest);

	Optional<WeightControl> findByIdAndUserId(Long id, Long userId);
}
