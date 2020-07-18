package com.sublimeprev.api.repository;

import java.util.Optional;

import com.sublimeprev.api.bases.BaseRepository;
import com.sublimeprev.api.model.Mother;
import com.sublimeprev.api.model.ProcessMother;

public interface ProcessMotherRepository extends BaseRepository<ProcessMother, Long>{
	Optional<ProcessMother> findByMother(Mother mother);
}
