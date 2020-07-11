//package com.cesararaujostudio.api.service;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.cesararaujostudio.api.model.User;
//import com.cesararaujostudio.api.repository.UserRepository;
//
//public class BaseServiceTest {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	public User junitUser() {
//		Optional<User> u = this.userRepository.findByUsername("junit");
//		return u.isPresent() ? u.get()
//				: this.userRepository.save(
//						User.builder().username("junit").name("Junit Test User").email("junit@3035tech.com").build());
//	}
//}
