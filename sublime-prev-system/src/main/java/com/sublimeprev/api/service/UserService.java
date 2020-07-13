package com.sublimeprev.api.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sublimeprev.api.bases.PageReq;
import com.sublimeprev.api.config.i18n.Messages;
import com.sublimeprev.api.config.i18n.ServiceException;
import com.sublimeprev.api.config.security.AuthUtil;
import com.sublimeprev.api.model.User;
import com.sublimeprev.api.repository.UserRepository;
import com.sublimeprev.api.util.SearchUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

	private final UserRepository repository;
	private final PasswordService passwordService;

	public Page<User> findAll(PageReq query) {
		Specification<User> deleted = SearchUtils.specByDeleted(query.isDeleted());
		Specification<User> filters = SearchUtils.specByFilter(query.getFilter(), "username", "id", "email", "name",
				"city", "phone");
		return this.repository.findAll(deleted.and(filters), query.toPageRequest());
	}

	public List<User> getAllUsers() {
		return repository.findAll();
	}

	public User findById(Long id) {
		return this.repository.findById(id).orElseThrow(() -> new ServiceException(Messages.record_not_found));
	}

	public User save(User pojo) {

		if (LocalDate.now().isBefore(pojo.getBirthdate()))
			throw new ServiceException(Messages.user_birthdate_invalid);
		if (StringUtils.hasText(pojo.getNewPassword())) {
			pojo.setEncryptedPassword(this.passwordService.encode(pojo.getNewPassword()));
		}
		if (pojo.getId() == null) {
			pojo.setEncryptedPassword(this.passwordService.encode(pojo.getEncryptedPassword()));
		}

		return this.findById(this.repository.save(pojo).getId());
	}

	public User saveProfile(User pojo) {

		if (LocalDate.now().isBefore(pojo.getBirthdate()))
			throw new ServiceException(Messages.user_birthdate_invalid);
		Optional<User> userOptional = this.repository.findByUsername(pojo.getUsername());
		if (userOptional.isPresent() && userOptional.get().getId() != pojo.getId()) {
			throw new ServiceException("Username já esta em uso.");

		} else {
			return this.findById(this.repository.save(pojo).getId());
		}

	}

	public void logicalExclusion(Long id) {
		if (!this.repository.findByIdAndNotDeleted(id).isPresent())
			throw new ServiceException(Messages.record_not_found);
		this.repository.softDelete(id);
	}

	public List<User> findAll() {
		return this.repository.findAll();
	}

	public List<User> findAllDeleted() {
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

	public List<User> findByIds(Long[] ids) {
		return this.repository.findAllById(Arrays.asList(ids));
	}

	public User findAuthenticatedUser() {
		return this.repository.findById(AuthUtil.getUserId())
				.orElseThrow(() -> new ServiceException("Usuário deve estar autenticado."));

	}
}
