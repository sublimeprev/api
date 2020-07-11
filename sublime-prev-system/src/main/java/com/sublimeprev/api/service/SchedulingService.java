package com.sublimeprev.api.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sublimeprev.api.config.i18n.Messages;
import com.sublimeprev.api.config.i18n.ServiceException;
import com.sublimeprev.api.domain.SchedulingStatus;
import com.sublimeprev.api.dto.req.SchedulingPageReq;
import com.sublimeprev.api.dto.res.AvailableDatesAndTimesResDTO;
import com.sublimeprev.api.dto.res.AvailableTimesResDTO;
import com.sublimeprev.api.model.AgendaConfig;
import com.sublimeprev.api.model.Scheduling;
import com.sublimeprev.api.model.Scheduling_;
import com.sublimeprev.api.model.TypeExercise;
import com.sublimeprev.api.model.User;
import com.sublimeprev.api.model.User_;
import com.sublimeprev.api.repository.SchedulingRepository;
import com.sublimeprev.api.repository.TimeOffRepository;
import com.sublimeprev.api.repository.TypeExerciseRepository;
import com.sublimeprev.api.repository.UserRepository;
import com.sublimeprev.api.util.SearchUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SchedulingService {

	private final SchedulingRepository repository;
	private final UserRepository userRepository;
	private final AgendaConfigService agendaConfigService;
	private final NotificationService notificationService;
	private final TimeOffRepository timeOffRepository;
	private final TypeExerciseRepository typeExerciseRepository;

	public Page<Scheduling> findAll(SchedulingPageReq query) {
		if (query.getSort() != null && query.getSort().length == 1 && query.getSort()[0].contains(Scheduling_.DATE)) {
			query.addSortItem(Scheduling_.TIME, "asc");
			query.addSortItem(Scheduling_.ID, "asc");
		}
		Specification<Scheduling> deleted = SearchUtils.specByDeleted(query.isDeleted());
		Specification<Scheduling> filters = SearchUtils.specByFilter(query.getFilter(), "id", "status",
				"date:localDate", "time:localTime", "user.name");
		Specification<Scheduling> date = (root, cQuery, builder) -> query.getDate() == null ? null
				: builder.equal(root.get(Scheduling_.DATE), LocalDate.parse(query.getDate()));
		Specification<Scheduling> time = (root, cQuery, builder) -> query.getTime() == null ? null
				: builder.equal(root.get(Scheduling_.TIME), LocalTime.parse(query.getTime(),
						new DateTimeFormatterBuilder().appendPattern("HH:mm").toFormatter()));
		Specification<Scheduling> user = (root, cQuery, builder) -> query.getUser() == null ? null
				: builder.like(builder.upper(root.get(Scheduling_.USER).get(User_.NAME)),
						"%" + query.getUser().toUpperCase() + "%");
		return this.repository.findAll(deleted.and(filters).and(date).and(time).and(user), query.toPageRequest());
	}

	public Scheduling findById(Long id) {
		return this.repository.findById(id).orElseThrow(() -> new ServiceException(Messages.record_not_found));
	}

	public Scheduling create(Long userId, LocalDate date, LocalTime time, SchedulingStatus status,
			Long idTypeExercise) {
		if (userId == null)
			throw new ServiceException("Usuário deve ser informado");
		if (idTypeExercise == null)
			throw new ServiceException("Tipo de treino deve ser informado");
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ServiceException("Usuário deve ser informado"));
		TypeExercise typeExercise = this.typeExerciseRepository.findById(idTypeExercise)
				.orElseThrow(() -> new ServiceException("Usuário deve ser informado"));
		Scheduling pojo = this.repository.findFirstByDateAndTimeAndUserId(date, time, userId).orElse(new Scheduling());
		if (pojo.getId() != null && !pojo.isDeleted() && !SchedulingStatus.CANCELADO.equals(pojo.getStatus()))
			throw new ServiceException("Já há um agendamento cadastrado para essa data e horário");
		pojo.setDate(date);
		pojo.setTime(time);
		pojo.setUser(user);
		pojo.setStatus(status);
		pojo.setDeleted(false);
		pojo.setTypeExercise(typeExercise);
		return this.save(pojo);
	}

	public Scheduling save(Scheduling pojo) {
		AgendaConfig config = this.agendaConfigService.findByDateAndTime(pojo.getDate(), pojo.getTime())
				.orElseThrow(() -> new ServiceException("Data e/ou horário informado indisponível"));

		if (this.timeOffRepository.existsByDateAndTimeOrTimeIsNull(pojo.getDate(), pojo.getTime()))
			throw new ServiceException("Data e/ou horário informado indisponível");

		Long countScheduled = pojo.getId() == null
				? this.repository.countByDateAndTimeAndStatus(pojo.getDate(), pojo.getTime(), SchedulingStatus.MARCADO)
				: this.repository.countByDateAndTimeAndStatusAndIdNot(pojo.getDate(), pojo.getTime(),
						SchedulingStatus.MARCADO, pojo.getId());
		if (countScheduled.intValue() >= config.getMaxLimit())
			throw new ServiceException(
					"Não foi possível realizar essa ação pois número máximo de agendamentos foi atingido para esse horário.");
		return this.findById(this.repository.save(pojo).getId());
	}

	public void logicalExclusion(Long id) {
		if (!this.repository.findByIdAndNotDeleted(id).isPresent())
			throw new ServiceException(Messages.record_not_found);
		this.repository.softDelete(id);
	}

	public List<Scheduling> findAll() {
		return this.repository.findAll();
	}

	public List<Scheduling> findAllDeleted() {
		return this.repository.findAllDeleted();
	}

	public void restoreDeleted(Long id) {
		if (!this.repository.findDeletedById(id).isPresent())
			throw new ServiceException(Messages.record_not_found_at_recycle_bin);
		this.repository.restoreDeleted(id);
	}

	public void permanentDestroy(Long id) {
		if (!this.repository.findDeletedById(id).isPresent())
			throw new ServiceException(Messages.record_not_found_at_recycle_bin);
		this.repository.deleteById(id);
	}

	public List<Scheduling> findByIds(Long[] ids) {
		return this.repository.findAllById(Arrays.asList(ids));
	}

	public List<AvailableDatesAndTimesResDTO> availableDatesAndTimes(Long idTypeExercise) {
		List<AvailableDatesAndTimesResDTO> list = new ArrayList<>();
		LocalDate date = LocalDate.now();
		for (int i = 0; i < 8; i++) {
			List<AvailableTimesResDTO> times = this.availableTimesByDate(date, idTypeExercise);
			list.add(AvailableDatesAndTimesResDTO.of(date, times));
			date = date.plusDays(1);
		}
		return list;
	}

	public List<AvailableTimesResDTO> availableTimesByDate(LocalDate date, Long idTypeExercise) {
		TypeExercise typeExercise = typeExerciseRepository.findById(idTypeExercise).get();
		List<AvailableTimesResDTO> times = new ArrayList<>();
		List<AgendaConfig> configs = this.agendaConfigService.findConfigsByDate(date);

		if (!configs.isEmpty()) {

			for (AgendaConfig config : configs) {
				boolean[] hasTrainingAvailable = { false };
				config.getTypesExercises().forEach(type -> {
					if (verifyObj(type, typeExercise)) {
						hasTrainingAvailable[0] = true;
					}
				});
				LocalTime time = config.getTime();
				long filled = this.repository.countByDateAndTimeAndStatus(date, time, SchedulingStatus.MARCADO);
				times.add(AvailableTimesResDTO.of(time, config.getMaxLimit(), (int) filled, hasTrainingAvailable[0]));
			}
		}
		return times;
	}

	public List<Scheduling> cancelMany(Long[] ids, String title, String content, boolean sendNotify) {
		List<Scheduling> schedulings = this.findByIds(ids).stream().map(item -> {
			item.setStatus(SchedulingStatus.CANCELADO);
			return item;
		}).collect(Collectors.toList());
		if (sendNotify) {
			this.notificationService.sendNotificationToUsers(
					schedulings.stream().map(item -> item.getUser().getId()).distinct().collect(Collectors.toList()),
					title, content);
		}
		return this.repository.saveAll(schedulings);
	}

	public List<Scheduling> getSchedulesByDate(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return repository.findAllByDate(localDate);
	}

	public void allLogicalExclusionByUser(Long id) {

		List<Scheduling> list = repository.findAllUser(userRepository.findById(id).get());

		list.forEach(scheduling -> {
			scheduling.setDeleted(true);
			repository.save(scheduling);
		});
	}

	private boolean verifyObj(TypeExercise type, TypeExercise typeExercise) {
		if (type.getId() == typeExercise.getId()) {
			return true;
		} else {
			return false;
		}
	}
}
