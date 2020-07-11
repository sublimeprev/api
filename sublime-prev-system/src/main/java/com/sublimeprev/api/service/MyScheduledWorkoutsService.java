package com.sublimeprev.api.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sublimeprev.api.bases.PageReq;
import com.sublimeprev.api.config.i18n.ServiceException;
import com.sublimeprev.api.config.security.AuthUtil;
import com.sublimeprev.api.domain.SchedulingStatus;
import com.sublimeprev.api.dto.res.AvailableDatesAndTimesResDTO;
import com.sublimeprev.api.model.Scheduling;
import com.sublimeprev.api.model.Scheduling_;
import com.sublimeprev.api.model.User_;
import com.sublimeprev.api.repository.SchedulingRepository;
import com.sublimeprev.api.util.SearchUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MyScheduledWorkoutsService {

	private final SchedulingRepository repository;
	private final SchedulingService schedulingService;
	private final NotificationService notificationService;
	private final TypeExerciseService typeExerciseService;
	private final EmailService emailService;

	public Page<Scheduling> findAllMine(PageReq query) {
		Specification<Scheduling> user = (root, qr, builder) -> builder.equal(root.get(Scheduling_.user).get(User_.id),
				AuthUtil.getUserId());
		Specification<Scheduling> deleted = SearchUtils.specByDeleted(query.isDeleted());
		Specification<Scheduling> filters = SearchUtils.specByFilter(query.getFilter(), "id", "date:localDate",
				"time:localTime");
		Specification<Scheduling> afterDate = (root, qr, builder) -> builder
				.greaterThanOrEqualTo(root.get(Scheduling_.date), LocalDate.now());
		Specification<Scheduling> scheduled = (root, qr, builder) -> builder.equal(root.get(Scheduling_.status),
				SchedulingStatus.MARCADO);
		return this.repository.findAll(user.and(deleted).and(afterDate).and(filters).and(scheduled), query.toPageRequest());
	}

	public Scheduling createNewScheduling(LocalDate date, LocalTime time, Long idTypeExercise) {
		Long userId = AuthUtil.getUserId();
		if (userId == null)
			throw new ServiceException("Usuário deve estar autenticado.");
		String title = "Novo Agendamento.";
		String content = "O Aluno " + AuthUtil.getUserName() + " marcou um treino do tipo: " +typeExerciseService.findById(idTypeExercise).getType() +
				" para a data " + date.toString() + " às " + time.toString()+".";
		this.notificationService.sendNotificationToAdmins(title, content);
		this.emailService.sendEmailAdmin(title, content);
		return this.schedulingService.create(userId, date, time, SchedulingStatus.MARCADO, idTypeExercise);
	}

	public Scheduling cancel(Long id) {
		Scheduling scheduling = this.repository.findByIdAndUserId(id, AuthUtil.getUserId())
				.orElseThrow(() -> new ServiceException("Agendamento não encontrado"));
		if(LocalTime.now().plusMinutes(30).isAfter(scheduling.getTime()) & scheduling.getDate().equals(LocalDate.now())) {
			throw new  ServiceException("Agendamento só pode ser desmarcado com mais de 30 minutos de antecedência.");
		}
		scheduling.setStatus(SchedulingStatus.CANCELADO);
		String title = "Novo Cancelamento.";
		String content = "O Aluno " + AuthUtil.getUserName() + " desmarcou um treino do tipo " + scheduling.getTypeExercise().getType() +
				", que estava marcado para " +  scheduling.getDate().toString() + ", às " + scheduling.getTime().toString()+".";
		this.notificationService.sendNotificationToAdmins(title, content);
		this.emailService.sendEmailAdmin(title, content);
		return this.repository.save(scheduling);
	}

	public List<AvailableDatesAndTimesResDTO> availableDatesAndTimes(Long idTypeExercise) {
		return this.schedulingService.availableDatesAndTimes(idTypeExercise);
	}
}
