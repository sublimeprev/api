package com.sublimeprev.api.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sublimeprev.api.config.i18n.Messages;
import com.sublimeprev.api.config.i18n.ServiceException;
import com.sublimeprev.api.model.Mother;
import com.sublimeprev.api.model.ProcessMother;
import com.sublimeprev.api.repository.ProcessMotherRepository;

@Service
public class ProcessMotherService {

	@Autowired
	private ProcessMotherRepository repository;

	@Autowired
	private MotherService motherService;

	public ProcessMother save(ProcessMother processMother, Long idMother) {
		if(processMother.getId() == null) {
			Mother mother = this.motherService.findById(idMother);
			processMother.setMother(mother);
			processMother.setDateStart(LocalDate.now());
		}
		return this.repository.save(processMother);
	}

	public ProcessMother findById(Long idAddresMother) {
		return this.repository.findById(idAddresMother)
				.orElseThrow(() -> new ServiceException("Mother not foud."));
	}
	
	public ProcessMother findByMother(Long idMother) {
		Mother mother = this.motherService.findById(idMother);
		
		return this.repository.findByMother(mother).orElseThrow(() -> new ServiceException("Mother not foud"));
	}

	public void logicalExclusion(Long id) {
		if (!this.repository.findByIdAndNotDeleted(id).isPresent())
			throw new ServiceException(Messages.record_not_found);
		this.repository.softDelete(id);
	}

	public void restoreDeleted(Long id) {
		if (!this.repository.findDeletedById(id).isPresent())
			throw new ServiceException(Messages.record_not_found_at_recycle_bin);
		this.repository.restoreDeleted(id);
	}

	public List<ProcessMother> findAllDeleted() {
		return this.repository.findAllDeleted();
	}

	public List<ProcessMother> findByIds(Long[] ids) {
		return this.repository.findAllById(Arrays.asList(ids));
	}

	public void permanentDestroy(Long id) {
		if (!this.repository.findDeletedById(id).isPresent())
			throw new ServiceException(Messages.record_not_found_at_recycle_bin);
		this.repository.deleteById(id);
	}

	public boolean verifyAddresMother(Long idMother) {
		Mother mother = this.motherService.findById(idMother);
		Optional<ProcessMother> optionalMother = this.repository.findByMother(mother);

		if (optionalMother.isPresent()) {
			return true;
		} else {
			return false;
		}
	}
	
	public ProcessMother findByCpfAndBirthdayMother(String cpf, String birthday) {
		LocalDate birth = LocalDate.parse(birthday);
		Mother mother = this.motherService.findByCpfAndBirthday(cpf, birth);
		return this.findByMother(mother.getId());
	}
}
