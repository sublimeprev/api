package com.sublimeprev.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.threeten.bp.LocalDate;

import com.sublimeprev.api.bases.PageReq;
import com.sublimeprev.api.config.i18n.Messages;
import com.sublimeprev.api.config.i18n.ServiceException;
import com.sublimeprev.api.model.Mother;
import com.sublimeprev.api.model.User;
import com.sublimeprev.api.repository.MotherRepository;
import com.sublimeprev.api.util.SearchUtils;

@Service
public class MotherService {

	@Autowired
	private MotherRepository repository;
	
	@Autowired
	private UserService userService;
	
	public Page<Mother> findAll(PageReq query) {
		Specification<Mother> deleted = SearchUtils.specByDeleted(query.isDeleted());
		Specification<Mother> filters = SearchUtils.specByFilter(query.getFilter(), "createdAt", "id", "email", "name",
				"city", "phone");
		return this.repository.findAll(deleted.and(filters), query.toPageRequest());
	}
	
	public List<Mother> getAllMothers() {
		return repository.findAll();
	}
	
	public Mother saveMother(Mother mother) {
		this.repository.findByMother(mother.getCpf()).orElseThrow(() -> new ServiceException("Mãe já esta cadastrada."));
		if(mother.getBirthdate().getDayOfYear() < LocalDate.now().getYear() - 10) {
			throw new ServiceException("Data de aniversário incorreto.");
		}
		LocalDateTime today = LocalDateTime.now();
		User userAuthenticated = userService.findAuthenticatedUser();
		mother.setCreatedAt(today);
		mother.setUpdatedBy(userAuthenticated.getName());
		return this.repository.save(mother);
	}
	
	public Mother updateMother(Mother mother) {
		LocalDateTime today = LocalDateTime.now();
		Mother motherCurrent = this.findById(mother.getId());
		motherCurrent.setBirthdate(mother.getBirthdate());
		motherCurrent.setCpf(mother.getCpf());
		motherCurrent.setEmail(mother.getEmail());
		motherCurrent.setFatherName(mother.getFatherName());
		motherCurrent.setMaritalStatus(mother.getMaritalStatus());
		motherCurrent.setMotherName(mother.getMotherName());
		motherCurrent.setName(mother.getName());
		motherCurrent.setPhone(mother.getPhone());
		motherCurrent.setPis(mother.getPis());
		motherCurrent.setRg(mother.getRg());
		motherCurrent.setSchooling(mother.getSchooling());
		motherCurrent.setUpdatedAt(today);
		return this.repository.save(motherCurrent);
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
}
