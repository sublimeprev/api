package com.sublimeprev.api.config.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.AllArgsConstructor;

@Configuration
public class SecurityConfig {

	private static final String CLIENT_ID = "cesargymapp";
	private static final String CLIENT_SECRET = "cesargymapp-secret";
	private static final int TOKEN_VALIDITY = 0;
	private static final String SCOPE = "all";
	private static final String RESOURCE_ID = "cesargym-service";

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
	public class GlobalMethodSecurityConfig extends GlobalMethodSecurityConfiguration {

		@Override
		public MethodSecurityExpressionHandler createExpressionHandler() {
			return new OAuth2MethodSecurityExpressionHandler();
		}
	}

	@Configuration
	@EnableWebSecurity
	public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.headers().frameOptions().sameOrigin();
			http.csrf().disable()
					.requestMatcher(request -> new AntPathRequestMatcher("/**").matches(request)
							&& (null == request.getHeader("Authorization")
									|| !String.valueOf(request.getHeader("Authorization")).contains("Bearer")))
					.authorizeRequests().anyRequest().permitAll().and().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().httpBasic();
		}
	}

	@Configuration
	@EnableAuthorizationServer
	@AllArgsConstructor
	public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

		private final TokenStore tokenStore;
		private final PasswordEncoder passwordEncoder;
		private final AuthenticationConfiguration authenticationConfiguration;

		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
			oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
			oauthServer.allowFormAuthenticationForClients();
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.addInterceptor(new HandlerInterceptorAdapter() {
				@Override
				public boolean preHandle(HttpServletRequest hsr, HttpServletResponse rs, Object o) throws Exception {
					rs.setHeader("Access-Control-Allow-Origin", "*");
					rs.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,OPTIONS");
					rs.setHeader("Access-Control-Max-Age", "3600");
					rs.setHeader("Access-Control-Allow-Headers",
							"Origin, X-Requested-With, Content-Type, Accept, Authorization");
					return true;
				}
			});

			endpoints.tokenStore(tokenStore).reuseRefreshTokens(true)
					.authenticationManager(authenticationConfiguration.getAuthenticationManager());
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.inMemory().withClient(CLIENT_ID).scopes(SCOPE).authorities("ROLE_APP_CLIENT")
					.authorizedGrantTypes("password", "refresh_token").secret(passwordEncoder.encode(CLIENT_SECRET))
					.accessTokenValiditySeconds(TOKEN_VALIDITY);
		}
	}

	@Configuration
	@EnableResourceServer
	public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.headers().frameOptions().disable();
			http.cors();
			http.requestMatcher(request -> new AntPathRequestMatcher("/**").matches(request)
					&& ((null != request.getHeader("Authorization")
							&& String.valueOf(request.getHeader("Authorization")).contains("Bearer")) || request.getParameter("access_token") != null))
					.authorizeRequests().anyRequest().permitAll();
		}

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources.resourceId(RESOURCE_ID).stateless(false);
		}
	}
}
