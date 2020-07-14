package com.sublimeprev.api.repository;

import java.util.Optional;

import com.sublimeprev.api.bases.BaseRepository;
import com.sublimeprev.api.model.AddressMother;
import com.sublimeprev.api.model.Mother;

public interface AddressMotherRepository extends BaseRepository<AddressMother, Long>{

	Optional<AddressMother> findByMother(Mother mother);

}
