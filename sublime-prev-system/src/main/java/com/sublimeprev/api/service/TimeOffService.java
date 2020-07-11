package com.sublimeprev.api.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sublimeprev.api.config.i18n.Messages;
import com.sublimeprev.api.config.i18n.ServiceException;
import com.sublimeprev.api.dto.req.TimeOffPageReq;
import com.sublimeprev.api.model.Scheduling_;
import com.sublimeprev.api.model.TimeOff;
import com.sublimeprev.api.repository.TimeOffRepository;
import com.sublimeprev.api.util.SearchUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TimeOffService {

	private final TimeOffRepository repository;

	public Page<TimeOff> findAll(TimeOffPageReq query) {
		Specification<TimeOff> deleted = SearchUtils.specByDeleted(query.isDeleted());
		Specification<TimeOff> filters = SearchUtils.specByFilter(query.getFilter(), "id", "date:localDate",
				"time:localTime");
		Specification<TimeOff> date = (root, cQuery, builder) -> query.getDate() == null ? null
				: builder.equal(root.get(Scheduling_.DATE), LocalDate.parse(query.getDate()));
		Specification<TimeOff> time = (root, cQuery, builder) -> query.getTime() == null ? null
				: builder.equal(root.get(Scheduling_.TIME), LocalTime.parse(query.getTime(),
						new DateTimeFormatterBuilder().appendPattern("HH:mm").toFormatter()));
		return this.repository.findAll(deleted.and(filters).and(date).and(time), query.toPageRequest());
	}

	public TimeOff findById(Long id) {
		return this.repository.findById(id).orElseThrow(() -> new ServiceException(Messages.record_not_found));
	}

	public TimeOff save(TimeOff pojo) {
		if (pojo.getTime() == null && this.repository.findByDateAndTimeIsNullAndDeletedIsFalse(pojo.getDate()).isPresent())
			throw new ServiceException("Data já cadastrada anteriormente.");

		if (pojo.getTime() != null && this.repository.findByDateAndTime(pojo.getDate(), pojo.getTime()).isPresent())
			throw new ServiceException("Data e hora já cadastrados anteriormente.");

		return this.findById(this.repository.save(pojo).getId());
	}

	public void logicalExclusion(Long id) {
		if (!this.repository.findByIdAndNotDeleted(id).isPresent())
			throw new ServiceException(Messages.record_not_found);
		this.repository.softDelete(id);
	}

	public List<TimeOff> findAll() {
		return this.repository.findAll();
	}

	public List<TimeOff> findAllDeleted() {
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

	public List<TimeOff> findByIds(Long[] ids) {
		return this.repository.findAllById(Arrays.asList(ids));
	}
}
