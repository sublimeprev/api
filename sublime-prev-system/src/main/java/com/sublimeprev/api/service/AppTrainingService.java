package com.sublimeprev.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sublimeprev.api.config.security.AuthUtil;
import com.sublimeprev.api.model.Training;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppTrainingService {

	private final TrainingByUserService service;
	private final FileStorageService fileStorageService;

	public ResponseEntity<byte[]> myLastTraining() {
		Training training = this.service.lastTraining(AuthUtil.getUserId());

//		byte[] file = this.fileStorageService.getByKey(training.getFileKey());
//		ByteArrayResource resource = new ByteArrayResource(file);
		byte[] file = this.fileStorageService.getByKey(training.getFileKey());


		return ResponseEntity.ok()
				.contentLength(file.length)
//				.header("Content-Disposition", "attachment; filename=" + training.getFileKey())
				.contentType(training.getMediaType())
				//.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(file);
	}
	
	/**
	 * @return
	 */
	public String myLastTrainingUrl() {
		String URL_FIREBASE = "https://firebasestorage.googleapis.com/v0/b/cesar-gym.appspot.com/o/FILE_NAME?alt=media";	
		Training training = this.service.lastTraining(AuthUtil.getUserId());
		return URL_FIREBASE.replace("FILE_NAME", training.getFileKey());
	}

}
