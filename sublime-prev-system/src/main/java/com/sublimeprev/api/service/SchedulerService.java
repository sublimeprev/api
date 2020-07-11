package com.sublimeprev.api.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sublimeprev.api.domain.SchedulingStatus;
import com.sublimeprev.api.model.AgendaConfig;
import com.sublimeprev.api.model.AgendaConfig_;
import com.sublimeprev.api.model.Scheduling;
import com.sublimeprev.api.model.TypeExercise;
import com.sublimeprev.api.model.User;
import com.sublimeprev.api.model.UserAgendaConfig;
import com.sublimeprev.api.repository.AgendaConfigRepository;
import com.sublimeprev.api.repository.SchedulingRepository;
import com.sublimeprev.api.repository.TimeOffRepository;
import com.sublimeprev.api.repository.TypeExerciseRepository;
import com.sublimeprev.api.repository.UserAgendaConfigRepository;
import com.sublimeprev.api.repository.UserRepository;
import com.sublimeprev.api.util.SearchUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SchedulerService {

	private static final String TIME_ZONE = "America/Sao_Paulo";
	private final AgendaConfigRepository agendaConfigRepository;
	private final UserAgendaConfigRepository userAgendaConfigRepository;
	private final SchedulingRepository schedulingRepository;
	private final TimeOffRepository timeOffRepository;
	private final UserRepository userRepository;
	private final TypeExerciseRepository TypeExerciseRepository;

//	private final static int DAYS_AHEAD = 15;
	private static final LocalDate today = LocalDate.now();
	private static final LocalDate lastDayOfMonth = today.withMonth(today.getMonthValue()).with(TemporalAdjusters.lastDayOfMonth());
	
	private static final  int DAYS_AHEAD = lastDayOfMonth.minusDays(today.getDayOfMonth()).getDayOfMonth();
	
	@Scheduled(cron = "0 0 23 * * *", zone = TIME_ZONE)
	public void cronJob() {
		this.createSchedulings();
	}

	public void createSchedulings() {
		List<AgendaConfig> allConfigs = this.agendaConfigRepository.findAll(SearchUtils.specByDeleted(false),
				Sort.by(Sort.Direction.ASC, AgendaConfig_.DAY_OF_WEEK, AgendaConfig_.TIME));
		LocalDate date = LocalDate.now();
		for (int i = 0; i < DAYS_AHEAD+1; i++) {
			List<AgendaConfig> configs = this.filterByDate(allConfigs, date);
			System.out.println("Date in: ".concat(date.toString()));
			System.out.println("DAYS_AHEAD: " + DAYS_AHEAD);
			System.out.println("Indice: " + i);
			System.out.println("Days ahead: " + DAYS_AHEAD);
			for (AgendaConfig config : configs) {
				LocalTime time = config.getTime();
				List<UserAgendaConfig> userAgendaConfigs = this.userAgendaConfigRepository.findAllByAgendaConfigId(config.getId());
				for (UserAgendaConfig userAgendaConfig : userAgendaConfigs) {
					
					Optional<User> user = userRepository.findById(userAgendaConfig.getUserId());
					Optional<TypeExercise> typeExercise = TypeExerciseRepository.findById(userAgendaConfig.getTypeExerciseId());
					Optional<Scheduling> opt = this.schedulingRepository.findFirstByDateAndTimeAndUserId(date, time,
							userAgendaConfig.getUserId());
					if (opt.isEmpty()) {
						if (!this.timeOffRepository.existsByDateAndTimeOrTimeIsNull(date, time)) {
							TypeExercise type = new TypeExercise();
							type = user.get().getTypesExercises().get(0);
							if(typeExercise.isPresent()){
								type = typeExercise.get();
							}
							Scheduling pojo = new Scheduling();
							pojo.setStatus(SchedulingStatus.MARCADO);
							pojo.setDate(date);
							pojo.setTime(time);
							pojo.setUser(user.get());
							pojo.setTypeExercise(type);
							this.schedulingRepository.save(pojo);
						}
					}
				}
			}
			date = date.plusDays(1);
		}
	}

	private List<AgendaConfig> filterByDate(List<AgendaConfig> allConfigs, LocalDate date) {
		return allConfigs.stream().filter(item -> date.getDayOfWeek().equals(item.getDayOfWeek()))
				.collect(Collectors.toList());

	}
}
