package com.sublimeprev.api.config;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableTransactionManagement
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver lr = new CookieLocaleResolver();
		lr.setDefaultLocale(new Locale("pt", "BR"));
		lr.setCookieName("client-lang");
		return lr;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*")
				.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH", "OPTIONS").allowCredentials(true);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		registry.addInterceptor(localeChangeInterceptor);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,
				new MediaType("application", "*+json"), MediaType.APPLICATION_OCTET_STREAM));
		converters.add(converter);
	}
}
