package com.api.sublimeprev.repository;

import com.api.sublimeprev.model.Mother;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotherRepository extends JpaRepository<Mother, Long> {
    @Query("SELECT * FROM Mother m WHERE m.deleted = false ")
    List<Mother> findAllIsDeletedFalse();
}
