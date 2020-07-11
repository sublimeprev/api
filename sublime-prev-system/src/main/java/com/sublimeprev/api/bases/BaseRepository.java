package com.sublimeprev.api.bases;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

	@Query("select e from #{#entityName} e where e.id = ?1")
	Optional<T> findById(ID id);

	@Query("select e from #{#entityName} e where e.id = ?1 and e.deleted is false")
	Optional<T> findByIdAndNotDeleted(ID id);

	@Override
	@Query("select e from #{#entityName} e where e.deleted is false")
	List<T> findAll();

	@Query("select e from #{#entityName} e where e.deleted is true")
	List<T> findAllDeleted();

	@Query("select e from #{#entityName} e where e.id = ?1 and e.deleted is true")
	Optional<T> findDeletedById(ID id);

	@Query("update #{#entityName} e set e.deleted=true where e.id = ?1")
	@Transactional
	@Modifying
	void softDelete(ID id);

	@Query("update #{#entityName} e set e.deleted=false where e.id = ?1")
	@Transactional
	@Modifying
	void restoreDeleted(ID id);
}