package com.sublimeprev.api.service;

import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sublimeprev.api.dto.common.TypeExerciseIdAgendaConfigIdCommonDTO;
import com.sublimeprev.api.dto.res.TimeItemResDTO;
import com.sublimeprev.api.dto.res.TypeExerciseResDTO;
import com.sublimeprev.api.dto.res.UserAgendaConfigResDTO;
import com.sublimeprev.api.model.AgendaConfig;
import com.sublimeprev.api.model.User;
import com.sublimeprev.api.model.UserAgendaConfig;
import com.sublimeprev.api.repository.AgendaConfigRepository;
import com.sublimeprev.api.repository.UserAgendaConfigRepository;
import com.sublimeprev.api.repository.UserRepository;
import com.sublimeprev.api.util.SearchUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserAgendaConfigService {

	private final UserAgendaConfigRepository repository;
	private final AgendaConfigRepository agendaConfigRepository;
	private final UserRepository userRepository;

	// SERVICES FROM USERS CRUD TAB

	public List<AgendaConfig> findAgendasConfigs() {
		return this.agendaConfigRepository.findAll(SearchUtils.specByDeleted(false));
	}
	
	public List<UserAgendaConfigResDTO> findConfigsByUser(Long userId) {
		User user = userRepository.findById(userId).get();
		List<AgendaConfig> configs = this.findAgendasConfigs();
		List<Long> agendasIdsOfUser = this.repository.findAgendasIdsByUserId(userId);

		LinkedHashMap<DayOfWeek, LinkedList<TimeItemResDTO>> map = configs.stream()
				.collect(Collectors.groupingBy(AgendaConfig::getDayOfWeek, LinkedHashMap::new, Collectors.mapping(
						item -> new TimeItemResDTO(item, agendasIdsOfUser, user.getTypesExercises()), Collectors.toCollection(LinkedList::new))));

		for (Map.Entry<DayOfWeek, LinkedList<TimeItemResDTO>> config : map.entrySet()) {
			config.setValue(config.getValue().stream().sorted(Comparator.comparing(TimeItemResDTO::getTime))
					.collect(Collectors.toCollection(LinkedList::new)));
		}
		
		return UserAgendaConfigResDTO.listUserAgendaConfigResDTO(map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey, (s1, s2) -> s1.compareTo(s2)))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new)), TypeExerciseResDTO.listTypeExerciseResDTO(user.getTypesExercises()));		
	}

	@Transactional
	public void saveConfigsByUser(Long userId, LinkedHashMap<DayOfWeek, LinkedList<TimeItemResDTO>> configs) {
		List<TypeExerciseIdAgendaConfigIdCommonDTO> typeExerciseIdAgendaConfigIdCommonDTO = new LinkedList<>();
		
		for (Map.Entry<DayOfWeek, LinkedList<TimeItemResDTO>> config : configs.entrySet()) {
			typeExerciseIdAgendaConfigIdCommonDTO
					.addAll(config.getValue().stream().filter(p -> p.isChecked())
							.collect(
									Collectors
											.mapping(
													p -> new TypeExerciseIdAgendaConfigIdCommonDTO(
															p.getTypeExerciseId(), p.getAgendaConfigId()),
													Collectors.toList())));
		}

		this.repository.deleteConfigsByUserId(userId);
		this.repository.saveAll(typeExerciseIdAgendaConfigIdCommonDTO.stream().map(dto -> new UserAgendaConfig(dto.getAgendaConfigId(), userId, dto.getTypeExerciseId()))
				.collect(Collectors.toList()));
	}

	// SERVICES FROM AGENDA_CONFIG CRUD TAB

	public List<User> findUsersByAgendaConfigId(Long agendaConfigId) {
		return this.repository.findUsersByAgendaConfigId(agendaConfigId);
	}

	public void insertUsersInAgendaConfig(Long agendaConfigId, List<Long> usersIds) {
		for (Long userId : usersIds) {
			UserAgendaConfig uac = this.repository.findFirstByUserIdAndAgendaConfigId(userId, agendaConfigId);
			if (uac == null) {
				uac = new UserAgendaConfig();
				uac.setAgendaConfigId(agendaConfigId);
				uac.setUserId(userId);
				this.repository.save(uac);
			}
		}
	}

	public void deleteUsersFromAgendaConfig(Long agendaConfigId, List<Long> usersIds) {
		this.repository.deleteInBatch(this.repository.findByAgendaConfigIdAndUserIdIn(agendaConfigId, usersIds));
	}
}
