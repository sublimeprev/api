package com.sublimeprev.api.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sublimeprev.api.config.AppProperties;
import com.sublimeprev.api.config.i18n.ServiceException;
import com.sublimeprev.api.firebase.FirebaseNotification;
import com.sublimeprev.api.model.Notification;
import com.sublimeprev.api.model.Scheduling;
import com.sublimeprev.api.model.User;
import com.sublimeprev.api.repository.NotificationRepository;
import com.sublimeprev.api.repository.SchedulingRepository;
import com.sublimeprev.api.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class NotificationService {

	private final NotificationRepository repository;
	private final UserRepository userRepository;
	private final AppProperties appProperties;
	private final TypeExerciseUserService typeExerciseUserService;
	private final SchedulingRepository schedulingRepository;
	private static final String TIME_ZONE = "America/Sao_Paulo";
	
	
	@Scheduled(cron = "0 00-59/1 * * * *", zone = TIME_ZONE)
	public void cronJob() {
		this.sendNotificationOneHourBeforeTraining();
	}
	
	public void sendNotificationOneHourBeforeTraining() {
		ZoneId timeZone = ZoneId.of(TIME_ZONE);
		List<Scheduling> listSchendulingToday = this.getSchedulesToday();
		String title = "Aviso de treino agendando";
		LocalTime hourNow = LocalTime.now(timeZone).plusHours(1);
		listSchendulingToday.forEach(scheduling -> {
			System.out.println("Hora time zone America/Sao_Paulo: " + LocalTime.now(timeZone).toString());
			if(scheduling.getTime().getHour() == hourNow.getHour() && scheduling.getTime().getMinute() ==  hourNow.getMinute()) {
				User user = scheduling.getUser();
				String content = "Olá " + user.getName() + ", lembrando que você tem um treino hoje às: " + scheduling.getTime()+".";
				this.sendNotificationToUser(user.getId(),title, content);
			}
		});
	}

	public void sendBroadcastNotification(String title, String content) {
		this.sendNotification(new ArrayList<>(), title, content);
		this.repository.save(Notification.builder().content(content).title(title).to(null).build());
	}

	public void sendNotificationToUser(Long userId, String title, String content) {
		User user = this.userRepository.findById(userId).orElse(null);
		if (StringUtils.isEmpty(user.getFirebaseToken()) || "null".equals(user.getFirebaseToken()))
			throw new ServiceException(
					"O usuário informado não possui token cadastrado para recebimento de notificações.");
		List<String> targets = Arrays.asList(user.getFirebaseToken());
		this.sendNotification(targets, title, content);
		this.repository.save(Notification.builder().content(content).title(title).to(user).build());
	}
	
	public void sendNotificationAllUsersByTypeExercise(Long typeExerciseId, String title, String content) {
		List<Long> usersIds = typeExerciseUserService.findAllUserIdsByTypeExerciseId(typeExerciseId);
		if (usersIds.isEmpty() || usersIds == null)
			throw new ServiceException(
					"O tipo de treino informado não possui nenhum usuário relacionado.");
		this.sendNotificationToUsers(usersIds, title, content);
	}

	public void sendNotificationToUsers(List<Long> usersIds, String title, String content) {
		List<User> listUsers = this.userRepository.findAllById(usersIds);
		List<String> targets = listUsers.stream().map(item -> item.getFirebaseToken())
				.filter(item -> item != null && !item.isBlank()).collect(Collectors.toList());
		if (!targets.isEmpty()) {
			this.sendNotification(targets, title, content);
			listUsers.forEach(user -> this.repository
					.save(Notification.builder().content(content).title(title).to(user).build()));
		}
	}

	private void sendNotification(List<String> targets, String title, String content) {
		FirebaseNotification.send(targets, title, content, this.appProperties.getFirebaseAndroidId(),
				appProperties.getFirebaseServerKey());
		if (!this.appProperties.getFirebaseAndroidId().equals(this.appProperties.getFirebaseIosId()))
			FirebaseNotification.send(targets, title, content, this.appProperties.getFirebaseIosId(),
					appProperties.getFirebaseServerKey());
	}
	
	public void sendNotificationToAdmins(String title, String content) {
		List<Long> usersIds = userRepository.findAllIdUsersAdmin();
		this.sendNotificationToUsers(usersIds, title, content);
	}
	
	private List<Scheduling>  getSchedulesToday(){
		LocalDate today = LocalDate.now();
		return schedulingRepository.findAllByDate(today);
	}
}
