package com.api.sublimeprev.repository;

import com.api.sublimeprev.model.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, Long> {
//    @Query("SELECT * FROM Consultant m WHERE m.deleted = false ")
//    List<Consultant> findAllIsDeletedFalse();
}
