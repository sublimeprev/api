package com.sublimeprev.api;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sublimeprev.api.domain.Role;
import com.sublimeprev.api.domain.SchedulingStatus;
import com.sublimeprev.api.model.AgendaConfig;
import com.sublimeprev.api.model.User;
import com.sublimeprev.api.repository.AgendaConfigRepository;
import com.sublimeprev.api.repository.UserRepository;
import com.sublimeprev.api.service.SchedulingService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AgendaConfigRepository agendaConfigRepository;
	private final SchedulingService schedulingService;

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		this.initDatabase();
	}

//	@SuppressWarnings("unused")
	private void initDatabase() {
		if (!this.userRepository.existsById(1L)) {
			HashSet<Role> roles = new HashSet<>(Arrays.asList(Role.ADMIN));
			this.userRepository.save(
					User.builder().username("admin").name("Administrador do Sistema").email("nicolas.dsw@gmail.com")
							.encryptedPassword(this.passwordEncoder.encode("1234")).roles(roles).build());

		}
		if (this.userRepository.findByUsername("teste1").isEmpty()) {
			for (int i = 1; i < 11; i++) {
				this.userRepository.save(User.builder().username("teste" + i).name("Tester " + i)
						.email("nicolas.dsw@gmail.com").encryptedPassword(this.passwordEncoder.encode("1234")).build());
			}
		}

		this.initAgendaConfig();
//		this.insertTestData();
	}

	public void initAgendaConfig() {
		if (this.agendaConfigRepository.count() == 0) {
			for (int i = 1; i <= 5; i++) {
				DayOfWeek dayOfWeek = DayOfWeek.of(i);
				for (int j = 7; j <= 18; j++) {
					LocalTime time = LocalTime.of(j, 0);
					Optional<AgendaConfig> config = this.agendaConfigRepository
							.findFirstByDayOfWeekAndTimeAndDeletedIsFalse(dayOfWeek, time);
					if (config.isEmpty()) {
						this.agendaConfigRepository
								.save(AgendaConfig.builder().dayOfWeek(dayOfWeek).time(time).maxLimit(8).build());
					}
				}
			}
		}
	}

	public void insertTestData() {
		for (long i = 1; i < 12; i++) {
			try {
				User user = this.userRepository.findById(i).get();
				LocalDate date = LocalDate.now();
				Long idTypeTraining = user.getTypesExercises().get(0).getId();
				for (int j = 0; j < 9; j++) {
					this.insertDateAndTime(user, date, LocalTime.of(10, 0),idTypeTraining);
					this.insertDateAndTime(user, date, LocalTime.of(14, 0), idTypeTraining);
					this.insertDateAndTime(user, date, LocalTime.of(16, 0), idTypeTraining);
					date = date.plusDays(1);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void insertDateAndTime(User user, LocalDate date, LocalTime time, Long idTypeExercise) {
		Optional<AgendaConfig> config = this.agendaConfigRepository
				.findFirstByDayOfWeekAndTimeAndDeletedIsFalse(date.getDayOfWeek(), time);
		if (config.isPresent())
			this.schedulingService.create(user.getId(), date, time, SchedulingStatus.MARCADO, idTypeExercise);
	}
}
