package com.sublimeprev.api.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import com.sublimeprev.api.config.i18n.ServiceException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

@Service
public class FileStorageService {

	private static final String BUCKET_NAME = "cesar-gym";
	private FirebaseApp firebaseApp;

	private FirebaseApp getFirebaseApp() {
		if (firebaseApp == null)
			try {
				InputStream serviceAccount = FileStorageService.class.getClassLoader().getResourceAsStream("cesar-gym-firebase-adminsdk-s3rqd-51d64c6b07.json");
				FirebaseOptions options = new FirebaseOptions.Builder().setStorageBucket(BUCKET_NAME + ".appspot.com")
						.setCredentials(GoogleCredentials.fromStream(serviceAccount))
						.setDatabaseUrl("https://cesar-gym.firebaseio.com").build();
				List<FirebaseApp> apps = FirebaseApp.getApps();
				firebaseApp = apps.isEmpty() ? FirebaseApp.initializeApp(options, "CesarGym") : apps.get(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return firebaseApp;
	}

	private Bucket getBucket() {
		return StorageClient.getInstance(getFirebaseApp()).bucket();
	}

	public String extractMimeType(final String encoded) {
		final Pattern mime = Pattern.compile("^data:([a-zA-Z0-9]+/[a-zA-Z0-9]+).*,.*");
		final Matcher matcher = mime.matcher(encoded);
		if (!matcher.find())
			return "";
		return matcher.group(1).toLowerCase();
	}

	public Blob uploadFile(String fileKey, String fileBase64) {
		try {
			byte[] fileBytes = Base64.decodeBase64((fileBase64.substring(fileBase64.indexOf(",") + 1)).getBytes());
			return this.getBucket().create(fileKey, fileBytes);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	public byte[] getByKey(String fileKey) {
		try {
			return this.getBucket().get(fileKey).getContent();
		} catch (Exception e) {
			return null;
		}
	}

	public String getBase64ByKey(String fileKey, String mimetype) {
		String prefix = mimetype == null ? "" : String.format("data:%s;base64,", mimetype);
		String base64 = fileKey == null ? null : Base64.encodeBase64String(this.getByKey(fileKey));
		return base64 == null ? null : prefix + base64;
	}

	public void deleteIfExists(String fileKey) {
		Blob blob = this.getBucket().get(fileKey);
		if (blob.exists())
			blob.delete();
	}
}
