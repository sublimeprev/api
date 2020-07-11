package com.sublimeprev.api.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sublimeprev.api.bases.PageReq;
import com.sublimeprev.api.config.i18n.Messages;
import com.sublimeprev.api.config.i18n.ServiceException;
import com.sublimeprev.api.model.AgendaConfig;
import com.sublimeprev.api.model.AgendaConfig_;
import com.sublimeprev.api.model.TypeExercise;
import com.sublimeprev.api.model.User;
import com.sublimeprev.api.repository.AgendaConfigRepository;
import com.sublimeprev.api.repository.TypeExerciseRepository;
import com.sublimeprev.api.util.SearchUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AgendaConfigService {

	private final AgendaConfigRepository repository;
	private final TypeExerciseRepository typeExerciseRepository;
	private final UserAgendaConfigService userAgendaConfigservice;
	
	public Page<AgendaConfig> findAll(PageReq query) {
		if (query.getSort() == null || query.getSort().length == 0)
			query.addSortItem(AgendaConfig_.DAY_OF_WEEK, "asc");
		if (query.getSort().length == 1) {
			if (query.getSort()[0].startsWith(AgendaConfig_.DAY_OF_WEEK))
				query.addSortItem(AgendaConfig_.TIME, "asc");
			else if (query.getSort()[0].startsWith(AgendaConfig_.TIME))
				query.addSortItem(AgendaConfig_.DAY_OF_WEEK, "asc");
		}
		Specification<AgendaConfig> deleted = SearchUtils.specByDeleted(query.isDeleted());
		Specification<AgendaConfig> filters = SearchUtils.specByFilter(query.getFilter(), "id", "time:localTime",
				"maxLimit", "dayOfWeek:dayOfWeek");
		return this.repository.findAll(deleted.and(filters), query.toPageRequest());
	}

	public AgendaConfig findById(Long id) {
		return this.repository.findById(id).orElseThrow(() -> new ServiceException(Messages.record_not_found));
	}

	public AgendaConfig save(AgendaConfig pojo) {
		return this.findById(this.repository.save(pojo).getId());
	}

	public void logicalExclusion(Long id) {
		if (!this.repository.findByIdAndNotDeleted(id).isPresent())
			throw new ServiceException(Messages.record_not_found);
		
		List<User> users = this.userAgendaConfigservice.findUsersByAgendaConfigId(id);
		if (!users.isEmpty()) 
			throw new ServiceException(Messages.agenda_config_bonded_users);
			
		this.repository.softDelete(id);
	}

	public List<AgendaConfig> findAll() {
		return this.repository.findAll();
	}

	public List<AgendaConfig> findAllDeleted() {
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

	public List<AgendaConfig> findByIds(Long[] ids) {
		return this.repository.findAllById(Arrays.asList(ids));
	}

	public Optional<AgendaConfig> findByDateAndTime(LocalDate date, LocalTime time) {
		return this.repository.findFirstByDayOfWeekAndTimeAndDeletedIsFalse(date.getDayOfWeek(), time);
	}

	public List<AgendaConfig> findConfigsByDate(LocalDate date) {
		return this.repository.findByDayOfWeekAndDeletedIsFalse(date.getDayOfWeek(),
				Sort.by(Sort.Direction.ASC, AgendaConfig_.TIME));
	}
	
	public List<AgendaConfig> findAllAgendaConfigDayOfWeekTypesExercise(DayOfWeek dayOfWeek, Long idTypeExercise){
		
		TypeExercise TypeExercise = typeExerciseRepository.findByIdAndNotDeleted(idTypeExercise).get();
		
		List<AgendaConfig> listAgendaConfig = repository.findByDayOfWeekAndTypesExercisesAndDeletedIsFalseOrderByTimeAsc(dayOfWeek, TypeExercise);
		
		if(listAgendaConfig.isEmpty() || listAgendaConfig == null) {
			throw new ServiceException("Não foi encontrado nenhum horário para este dia da semana.");
		}
		
		return listAgendaConfig;
	}
}
