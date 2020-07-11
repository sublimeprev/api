package com.sublimeprev.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sublimeprev.api.config.security.AuthUtil;
import com.sublimeprev.api.model.Evaluation;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppEvaluationService {
	
	private final EvaluationByUserService service;
	private final FileStorageService fileStorageService;
	
	public ResponseEntity<byte[]> myLastEvaluation() {
		Evaluation evaluation = this.service.lastEvaluation(AuthUtil.getUserId());
//		byte[] file = this.fileStorageService.getByKey(evaluation.getFileKey());
//		ByteArrayResource resource = new ByteArrayResource(file);
		byte[] file = this.fileStorageService.getByKey(evaluation.getFileKey());
		
		return ResponseEntity.ok()
				.contentLength(file.length)
				.header("Content-Disposition", "attachment; filename=" + evaluation.getFileKey())
				.contentType(evaluation.getMediaType())
				//.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(file);	
	}
	
	public String myLastEvaluationUrl() {
		String URL_FIREBASE = "https://firebasestorage.googleapis.com/v0/b/cesar-gym.appspot.com/o/FILE_NAME?alt=media";
		Evaluation evaluation = this.service.lastEvaluation(AuthUtil.getUserId());
		return URL_FIREBASE.replace("FILE_NAME", evaluation.getFileKey());
	}
	
	
}
