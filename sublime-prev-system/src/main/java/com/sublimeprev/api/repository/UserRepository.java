package com.sublimeprev.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.sublimeprev.api.bases.BaseRepository;
import com.sublimeprev.api.model.User;

public interface UserRepository extends BaseRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	@Query(value = "select users.id from users inner join user_roles on users.id = user_roles.user_id where users.deleted  = false",
			nativeQuery = true)
	List<Long> findAllIdUsersAdmin();
}