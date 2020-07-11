//package com.cesararaujostudio.api.service;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.UUID;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import com.cesararaujostudio.api.config.i18n.MessageFactory;
//import com.cesararaujostudio.api.config.i18n.Messages;
//import com.cesararaujostudio.api.config.i18n.ServiceException;
//import com.cesararaujostudio.api.model.User;
//import com.cesararaujostudio.api.service.UserService;
//
//@ActiveProfiles(profiles = "test")
//@SpringBootTest
//public class UserServiceTest {
//
//	@Autowired
//	private UserService service;
//
//	private User newTestObject() {
//		User pojo = new User();
//		pojo.setUsername("junit.test" + UUID.randomUUID().toString());
//		pojo.setEmail(pojo.getUsername() + "@3035tech.com");
//		pojo.setName("Junit Test");
//		pojo.setBirthdate(LocalDate.of(2000, 1, 1));
//		return pojo;
//	}
//
//	@Test
//	public void findAllTest() {
//		// when
//		List<User> list = this.service.findAll();
//		// then
//		assertNotNull(list);
//	}
//
//	@Test
//	public void findByIdExceptionTest() {
//		// given
//		Long id = null;
//		// when
//		Assertions.assertThrows(ServiceException.class, () -> {
//			this.service.findById(id);
//		}, MessageFactory.getMessage(Messages.record_not_found));
//		// then
//		// throws ServiceException
//	}
//
//	@Test
//	public void saveTest() {
//		// given
//		User pojo = this.newTestObject();
//		// when
//		pojo = this.service.save(pojo);
//		// then
//		assertNotNull(pojo.getId());
//	}
//
//	@Test
//	public void logicalExclusionTest() {
//		// given
//		User pojo = this.newTestObject();
//		pojo = this.service.save(pojo);
//		// when
//		this.service.logicalExclusion(pojo.getId());
//		// then
//		assertTrue(this.service.findAllDeleted().contains(pojo));
//		assertFalse(this.service.findAll().contains(pojo));
//	}
//
//	@Test
//	public void logicalExclusionExceptionTest() {
//		// given
//		User pojo = new User();
//		// when
//		Assertions.assertThrows(ServiceException.class, () -> {
//			this.service.logicalExclusion(pojo.getId());
//		}, MessageFactory.getMessage(Messages.record_not_found));
//		// then
//		// throws ServiceException
//	}
//
//	@Test
//	public void findAllDeletedTest() {
//		// given
//		User pojo = this.service.save(this.newTestObject());
//		this.service.logicalExclusion(pojo.getId());
//		// when
//		List<User> list = this.service.findAllDeleted();
//		// then
//		assertTrue(list.contains(pojo));
//	}
//
//	@Test
//	public void restoreDeletedTest() {
//		// given
//		User pojo = this.service.save(this.newTestObject());
//		this.service.logicalExclusion(pojo.getId());
//		// when
//		this.service.restoreDeleted(pojo.getId());
//		// then
//		assertTrue(this.service.findAll().contains(pojo));
//		assertFalse(this.service.findAllDeleted().contains(pojo));
//	}
//
//	@Test
//	public void restoreDeletedExceptionTest() {
//		// given
//		User pojo = new User();
//		// when
//		Assertions.assertThrows(ServiceException.class, () -> {
//			this.service.restoreDeleted(pojo.getId());
//		}, MessageFactory.getMessage(Messages.record_not_found_at_recycle_bin));
//		// then
//		// throws ServiceException
//	}
//
//	@Test
//	public void permanentDestroyTest() {
//		// given
//		User pojo = this.service.save(this.newTestObject());
//		this.service.logicalExclusion(pojo.getId());
//		// when
//		this.service.permanentDestroy(pojo.getId());
//		// then
//		assertFalse(this.service.findAll().contains(pojo));
//		assertFalse(this.service.findAllDeleted().contains(pojo));
//	}
//
//	@Test
//	public void permanentDestroyExceptionTest() {
//		// given
//		User pojo = new User();
//		// when
//		Assertions.assertThrows(ServiceException.class, () -> {
//			this.service.permanentDestroy(pojo.getId());
//		}, MessageFactory.getMessage(Messages.record_not_found_at_recycle_bin));
//		// then
//		// throws ServiceException
//	}
//}
