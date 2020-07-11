package com.sublimeprev.api.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sublimeprev.api.config.i18n.Messages;
import com.sublimeprev.api.config.i18n.ServiceException;
import com.sublimeprev.api.config.security.AuthUtil;
import com.sublimeprev.api.dto.req.WeightControlPageReq;
import com.sublimeprev.api.model.WeightControl;
import com.sublimeprev.api.repository.WeightControlRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MyWeightControlsService {

	private final WeightControlRepository repository;
	private final WeightControlByUserService weightControlByUserService;
	private final ProfileService profileService;

	public Page<WeightControl> findAllMine(WeightControlPageReq query) {
		return this.weightControlByUserService.findAll(AuthUtil.getUserId(), query);
	}

	public WeightControl findById(Long id) {
		return this.repository.findByIdAndUserId(id, AuthUtil.getUserId())
				.orElseThrow(() -> new ServiceException(Messages.record_not_found));
	}

	public WeightControl insert(Double value) {
		if (value == null)
			throw new ServiceException("O peso deve ser informado");
		WeightControl pojo = new WeightControl();
		pojo.setUser(this.profileService.findAuthenticatedUser());
		pojo.setDate(LocalDate.now());
		pojo.setValue(value);
		return this.findById(this.repository.save(pojo).getId());
	}

	public void logicalExclusion(Long id) {
		if (!this.repository.findByIdAndUserId(id, AuthUtil.getUserId()).isPresent())
			throw new ServiceException(Messages.record_not_found);
		this.repository.softDelete(id);
	}
}
