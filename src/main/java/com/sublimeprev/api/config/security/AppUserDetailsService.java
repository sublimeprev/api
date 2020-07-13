package com.sublimeprev.api.config.security;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sublimeprev.api.model.User;
import com.sublimeprev.api.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByUsername(username).orElse(null);
		if (user == null)
			throw new UsernameNotFoundException("user not found");
		Collection<GrantedAuthority> authorities = CollectionUtils.isEmpty(user.getRoles()) ? Collections.emptyList()
				: user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.toString()))
						.collect(Collectors.toList());
		return new SystemUser(user.getId(), user.getUsername(), user.getEncryptedPassword(), !user.isDeleted(),
				authorities, user.getName(), user.getEmail());
	}
}
