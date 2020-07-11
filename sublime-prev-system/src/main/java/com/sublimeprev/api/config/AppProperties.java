package com.sublimeprev.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {

	private String resetPasswordLink;
	private String confirmEmailLink;
	private String emailFrom;
	private String appPrefix;
	private String firebaseAndroidId;
	private String firebaseIosId;
	private String firebaseServerKey;
}
