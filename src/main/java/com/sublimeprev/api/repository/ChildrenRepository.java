package com.sublimeprev.api.repository;

import java.util.Optional;

import com.sublimeprev.api.bases.BaseRepository;
import com.sublimeprev.api.model.Children;
import com.sublimeprev.api.model.Mother;

public interface ChildrenRepository extends BaseRepository<Children, Long>{
	
	Optional<Children> findByMother(Mother mother);

}
