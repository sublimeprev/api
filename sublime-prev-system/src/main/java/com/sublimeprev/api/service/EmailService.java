package com.sublimeprev.api.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.sublimeprev.api.config.AppProperties;
import com.sublimeprev.api.model.User;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class EmailService {

	private final JavaMailSender mailSender;
	private final AppProperties appProperties;
	private final Configuration freemarkerConfig;
	private ScheduledExecutorService quickService;

	public static final String SUBJECT_PREFIX = "Cesar Araujo Personal & Running - ";

	public EmailService(JavaMailSender mailSender, AppProperties appProperties, Configuration freemarkerConfig) {
		this.mailSender = mailSender;
		this.appProperties = appProperties;
		this.freemarkerConfig = freemarkerConfig;
		this.quickService = Executors.newScheduledThreadPool(20);
	}

	public void sendEmail(User to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to.getEmail());
		message.setFrom(appProperties.getEmailFrom());
		message.setSubject(subject);
		message.setText(text);
		this.quickService.submit(() -> {
			try {
				mailSender.send(message);
				log.info("Success: sendEmail: " + to.getEmail());
			} catch (Exception e) {
				log.error("Error: sendEmail: " + to.getEmail() + " - " + e.getMessage());
			}
		});
	}
	
	public void sendForgotPasswordEmail(User user) {
		String templateName = "forgot_password.html";
		Map<String, String> emailContent = new HashMap<>();
		emailContent.put("user_name", user.getName());
		emailContent.put("link", this.appProperties.getResetPasswordLink() + user.getResetToken());
		this.quickService.submit(() -> {
			try {
				MimeMessage message = mailSender.createMimeMessage();
				message.setSubject(SUBJECT_PREFIX + "Requisição para alteração de senha");
				Template template = freemarkerConfig.getTemplate(templateName);
				String body = FreeMarkerTemplateUtils.processTemplateIntoString(template, emailContent);
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setTo(user.getEmail());
				helper.setFrom(appProperties.getEmailFrom());
				helper.setText(body, true);
				mailSender.send(message);
			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
			}
		});
	}

	public void sendConfirmEmail(User user) {
		String templateName = "confirm_email.html";
		Map<String, String> emailContent = new HashMap<>();
		emailContent.put("user_name", user.getName());
		emailContent.put("link", this.appProperties.getConfirmEmailLink() + user.getResetToken());
		this.quickService.submit(() -> {
			try {
				MimeMessage message = mailSender.createMimeMessage();
				message.setSubject(SUBJECT_PREFIX + "Confirme o seu e-mail");
				Template template = freemarkerConfig.getTemplate(templateName);
				String body = FreeMarkerTemplateUtils.processTemplateIntoString(template, emailContent);
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setTo(user.getEmail());
				helper.setFrom(appProperties.getEmailFrom());
				helper.setText(body, true);
				mailSender.send(message);
			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
			}
		});
	}
	
	public void sendEmailAdmin(String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("cesargymapp@gmail.com");
		message.setFrom("cesargymapp@gmail.com");
		message.setSubject(subject);
		message.setText(text);
		this.quickService.submit(() -> {
			try {
				mailSender.send(message);
				log.info("Success: sendEmail: cesargymapp@gmail.com");
			} catch (Exception e) {
				log.error("Error: sendEmail: cesargymapp@gmail.com" + " - " + e.getMessage());
			}
		});
	}
	
}
