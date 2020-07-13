package com.sublimeprev.api.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sublimeprev.api.config.i18n.Messages;
import com.sublimeprev.api.config.i18n.ServiceException;
import com.sublimeprev.api.model.AddressMother;
import com.sublimeprev.api.model.Mother;
import com.sublimeprev.api.repository.AddressMotherRepository;

@Service
public class AddressMotherService {
	@Autowired
	private AddressMotherRepository repository;
	
	@Autowired
	private MotherService motherService;
	
	public AddressMother save(AddressMother addresMother, Long idMother) {
		Mother mother = this.motherService.findById(idMother);
		addresMother.setMother(mother);
		return this.repository.save(addresMother);
	}
	
	public AddressMother findById(Long idAddresMother) {
		return this.repository.findById(idAddresMother).orElseThrow(() -> new ServiceException("Endereço não encontrado."));
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
	
	public List<AddressMother> findAllDeleted() {
		return this.repository.findAllDeleted();
	}
	
	public List<AddressMother> findByIds(Long[] ids) {
		return this.repository.findAllById(Arrays.asList(ids));
	}
	
	public void permanentDestroy(Long id) {
		if (!this.repository.findDeletedById(id).isPresent())
			throw new ServiceException(Messages.record_not_found_at_recycle_bin);
		this.repository.deleteById(id);
	}
	
}
