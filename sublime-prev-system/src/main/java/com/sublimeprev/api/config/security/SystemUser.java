package com.sublimeprev.api.config.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class SystemUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5601372174661369210L;

	private Long id;
	private String name;
	private String email;

	public SystemUser(Long id, String username, String password, boolean enabled,
			Collection<? extends GrantedAuthority> authorities, String name, String email) {
		super(username, password, enabled, true, true, true, authorities);
		this.id = id;
		this.name = name;
		this.email = email;
	}

}
