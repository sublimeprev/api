package com.api.sublimeprev.repository;

import com.api.sublimeprev.model.Mother;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotherRepository extends JpaRepository<Mother, Long> {
//    @Query("SELECT * FROM Mother m WHERE m.deleted = false ")
//    List<Mother> findAllIsDeletedFalse();
}
