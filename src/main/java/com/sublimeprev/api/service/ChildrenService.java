package com.sublimeprev.api.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sublimeprev.api.config.i18n.Messages;
import com.sublimeprev.api.config.i18n.ServiceException;
import com.sublimeprev.api.model.Children;
import com.sublimeprev.api.model.Mother;
import com.sublimeprev.api.repository.ChildrenRepository;

@Service
public class ChildrenService {
	
	@Autowired
	private ChildrenRepository repository;
	
	@Autowired
	private MotherService motherService;
	
	public Children save(Children children, Long idMother) {
		if(children.getId() == null) {
			Mother mother = this.motherService.findById(idMother);
			children.setMother(mother);
		}
		return this.repository.save(children);
	}

	public Children findById(Long idAddresMother) {
		return this.repository.findById(idAddresMother)
				.orElseThrow(() -> new ServiceException("Endereço não encontrado."));
	}
	
	public Children findByMother(Long idMother) {
		Mother mother = this.motherService.findById(idMother);
		
		return this.repository.findByMother(mother).orElseThrow(() -> new ServiceException("Children not foud"));
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

	public List<Children> findAllDeleted() {
		return this.repository.findAllDeleted();
	}

	public List<Children> findByIds(Long[] ids) {
		return this.repository.findAllById(Arrays.asList(ids));
	}

	public void permanentDestroy(Long id) {
		if (!this.repository.findDeletedById(id).isPresent())
			throw new ServiceException(Messages.record_not_found_at_recycle_bin);
		this.repository.deleteById(id);
	}

	public boolean verifyAddresMother(Long idMother) {
		Mother mother = this.motherService.findById(idMother);
		Optional<Children> optionalMother = this.repository.findByMother(mother);

		if (optionalMother.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

}
