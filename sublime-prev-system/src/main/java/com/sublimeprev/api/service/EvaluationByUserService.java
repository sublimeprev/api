package com.sublimeprev.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sublimeprev.api.bases.PageReq;
import com.sublimeprev.api.config.i18n.Messages;
import com.sublimeprev.api.config.i18n.ServiceException;
import com.sublimeprev.api.model.Evaluation;
import com.sublimeprev.api.model.Evaluation_;
import com.sublimeprev.api.model.User;
import com.sublimeprev.api.model.User_;
import com.sublimeprev.api.repository.EvaluationRepository;
import com.sublimeprev.api.repository.UserRepository;
import com.sublimeprev.api.util.SearchUtils;
import com.google.cloud.storage.Blob;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EvaluationByUserService {

	private final EvaluationRepository repository;
	private final UserRepository userRepository;
	private final FileStorageService fileStorageService;
	private final NotificationService notificationService;

	public Page<Evaluation> findAll(Long userId, PageReq query) {
		Specification<Evaluation> user = (root, qr, builder) -> builder.equal(root.get(Evaluation_.user).get(User_.id),
				userId);
		Specification<Evaluation> deleted = SearchUtils.specByDeleted(query.isDeleted());
		Specification<Evaluation> filters = SearchUtils.specByFilter(query.getFilter(), "id", "fileKey", "size",
				"mimetype", "createdAt:localDatetime");
		return this.repository.findAll(user.and(deleted).and(filters), query.toPageRequest());
	}

	public Evaluation findById(Long userId, Long id) {
		Evaluation pojo = this.repository.findByUserIdAndIdAndDeletedFalse(userId, id)
				.orElseThrow(() -> new ServiceException(Messages.record_not_found));
		pojo.setEncodedContent(this.fileStorageService.getBase64ByKey(pojo.getFileKey(), pojo.getMimetype()));
		return pojo;
	}

	public Evaluation findByIdWithoutContent(Long userId, Long id) {
		Evaluation pojo = this.repository.findByUserIdAndIdAndDeletedFalse(userId, id)
				.orElseThrow(() -> new ServiceException(Messages.record_not_found));
		return pojo;
	}

	public Evaluation save(Long userId, Evaluation pojo) {
		if (pojo.getId() != null)
			this.findById(userId, pojo.getId());
		pojo.setUser(this.userRepository.findById(userId).get());
		return this.findById(userId, this.repository.save(pojo).getId());
	}

	@Transactional
	public Evaluation save(Long userId, Evaluation pojo, String newFileName, String newFileBase64) {
		if (pojo.getId() == null && (newFileBase64 == null || newFileBase64.isBlank())) {
			throw new ServiceException("Deve-se adicionar o anexo.");
		}
		pojo = this.save(userId, pojo);
		if (newFileBase64 != null) {
			String fileKey = String.format("evaluation_%d_%s", pojo.getId(),
					newFileName == null ? "unnamed" : newFileName);
			if (pojo.getFileKey() != null) {
				this.fileStorageService.deleteIfExists(pojo.getFileKey());
			}
			Blob storedBlob = this.fileStorageService.uploadFile(fileKey, newFileBase64);
			pojo.setFileKey(fileKey);
			pojo.setSize(storedBlob.getSize());
			pojo.setMimetype(this.fileStorageService.extractMimeType(newFileBase64));
			pojo = this.repository.save(pojo);
		}
		User user = userRepository.findById(userId).get();
		String title = "Avaliação";
		String content = "Olá " + user.getName() + ", você tem uma nova avaliação disponível.";
		this.notificationService.sendNotificationToUser(userId, title, content);
		return pojo;
	}

	public void logicalExclusion(Long userId, Long id) {
		if (!this.repository.findByUserIdAndIdAndDeletedFalse(userId, id).isPresent())
			throw new ServiceException(Messages.record_not_found);
		this.repository.softDelete(id);
	}

	public Evaluation lastEvaluation(Long userId) {
		Evaluation pojo = this.repository
				.findFirstByUserIdAndDeletedFalse(userId, Sort.by(Sort.Direction.DESC, Evaluation_.CREATED_AT))
				.orElseThrow(() -> new ServiceException("Nenhum treino cadastrado."));
		pojo.setEncodedContent(this.fileStorageService.getBase64ByKey(pojo.getFileKey(), pojo.getMimetype()));
		return pojo;
	}
}
