package com.sublimeprev.api.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sublimeprev.api.config.i18n.Messages;
import com.sublimeprev.api.config.i18n.ServiceException;
import com.sublimeprev.api.model.LastCompany;
import com.sublimeprev.api.model.Mother;
import com.sublimeprev.api.repository.LastCompanyRepository;

@Service
public class LastCompanyService {
	
	@Autowired
	private LastCompanyRepository repository;
	
	@Autowired
	private MotherService motherService;
	
	public LastCompany save(LastCompany lastCompany, Long idMother) {
		if(lastCompany.getId() == null) {
			Mother mother = this.motherService.findById(idMother);
			lastCompany.setMother(mother);
		}
		return this.repository.save(lastCompany);
	}

	public LastCompany findById(Long idLastCompany) {
		return this.repository.findById(idLastCompany)
				.orElseThrow(() -> new ServiceException("LastCompany not found."));
	}
	
	public LastCompany findByMother(Long idMother) {
		Mother mother = this.motherService.findById(idMother);
		
		return this.repository.findByMother(mother).orElseThrow(() -> new ServiceException("LastCompany not foud"));
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

	public List<LastCompany> findAllDeleted() {
		return this.repository.findAllDeleted();
	}

	public List<LastCompany> findByIds(Long[] ids) {
		return this.repository.findAllById(Arrays.asList(ids));
	}

	public void permanentDestroy(Long id) {
		if (!this.repository.findDeletedById(id).isPresent())
			throw new ServiceException(Messages.record_not_found_at_recycle_bin);
		this.repository.deleteById(id);
	}

	public boolean verifyAddresMother(Long idMother) {
		Mother mother = this.motherService.findById(idMother);
		Optional<LastCompany> optionalMother = this.repository.findByMother(mother);

		if (optionalMother.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

}
