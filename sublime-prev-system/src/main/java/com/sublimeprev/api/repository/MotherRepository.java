package com.sublimeprev.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.sublimeprev.api.bases.BaseRepository;
import com.sublimeprev.api.model.Mother;

public interface MotherRepository extends BaseRepository<Mother, Long>{
	@Query("select m from Mother m where m.cpf = ?1 and m.deleted is true")
	Optional<Mother> findByMother(String cpf);

}
