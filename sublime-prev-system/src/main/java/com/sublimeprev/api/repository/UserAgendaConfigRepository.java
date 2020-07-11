package com.sublimeprev.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sublimeprev.api.model.User;
import com.sublimeprev.api.model.UserAgendaConfig;

public interface UserAgendaConfigRepository extends JpaRepository<UserAgendaConfig, Long>, JpaSpecificationExecutor<UserAgendaConfig> {

	@Query("SELECT uac.agendaConfigId FROM UserAgendaConfig uac WHERE uac.userId = ?1")
	List<Long> findAgendasIdsByUserId(Long userId);
	
	@Modifying
	@Query("DELETE FROM UserAgendaConfig uac WHERE uac.userId = ?1")
	void deleteConfigsByUserId(Long userId);
	
	@Query("SELECT u FROM UserAgendaConfig uac JOIN User u ON u.id = uac.userId WHERE uac.agendaConfigId = ?1 AND u.deleted is false")
	List<User> findUsersByAgendaConfigId(Long agendaConfigId);
	
	@Query("SELECT uac FROM UserAgendaConfig uac WHERE uac.agendaConfigId = ?1")
	List<UserAgendaConfig> findAllByAgendaConfigId(Long agendaConfigId);

	UserAgendaConfig findFirstByUserIdAndAgendaConfigId(Long userId, Long agendaConfigId);
	
	List<UserAgendaConfig> findByAgendaConfigIdAndUserIdIn(Long agendaConfigId, List<Long> usersIds);
}
