package com.sublimeprev.api.repository;

import java.util.Optional;

import com.sublimeprev.api.bases.BaseRepository;
import com.sublimeprev.api.model.LastCompany;
import com.sublimeprev.api.model.Mother;

public interface LastCompanyRepository extends BaseRepository<LastCompany, Long>{
	Optional<LastCompany> findByMother(Mother mother);
}
