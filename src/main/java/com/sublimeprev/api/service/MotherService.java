package com.sublimeprev.api.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sublimeprev.api.bases.PageReq;
import com.sublimeprev.api.config.i18n.Messages;
import com.sublimeprev.api.config.i18n.ServiceException;
import com.sublimeprev.api.model.Mother;
import com.sublimeprev.api.repository.MotherRepository;
import com.sublimeprev.api.util.SearchUtils;

@Service
public class MotherService {

	@Autowired
	private MotherRepository repository;
	
	public Page<Mother> findAll(PageReq query) {
		Specification<Mother> deleted = SearchUtils.specByDeleted(query.isDeleted());
		Specification<Mother> filters = SearchUtils.specByFilter(query.getFilter(), "createdAt", "id", "email", "name",
				"city", "phone");
		return this.repository.findAll(deleted.and(filters), query.toPageRequest());
	}
	
	public List<Mother> getAllMothers() {
		return repository.findAll();
	}
	
	public Mother save(Mother mother) {
		if(this.repository.findByMother(mother.getCpf()).isPresent() && mother.getId() == null) {
			new ServiceException("Mãe já esta cadastrada.");
		}
		if(mother.getCpf().isEmpty() || mother.getCpf() == null) {
			throw new ServiceException("Campo cpf deve estar preenchido corretamente.");
		}
		return this.repository.save(mother);
	}
	
	public Mother findById(Long IdMother) {
		return this.repository.findById(IdMother).orElseThrow(() -> new ServiceException("Mãe não cadastrada."));
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
	
	public List<Mother> findAllDeleted() {
		return this.repository.findAllDeleted();
	}
	
	public List<Mother> findByIds(Long[] ids) {
		return this.repository.findAllById(Arrays.asList(ids));
	}
	
	public void permanentDestroy(Long id) {
		if (!this.repository.findDeletedById(id).isPresent())
			throw new ServiceException(Messages.record_not_found_at_recycle_bin);
		this.repository.deleteById(id);
	}
}
