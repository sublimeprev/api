//package com.cesararaujostudio.api.service;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.List;
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
//import com.cesararaujostudio.api.domain.SchedulingStatus;
//import com.cesararaujostudio.api.model.Scheduling;
//import com.cesararaujostudio.api.service.SchedulingService;
//
//@ActiveProfiles(profiles = "test")
//@SpringBootTest
//public class SchedulingServiceTest extends BaseServiceTest {
//
//	@Autowired
//	private SchedulingService service;
//
//	private Scheduling newTestObject() {
//		Scheduling pojo = new Scheduling();
//		pojo.setUser(junitUser());
//		pojo.setDate(LocalDate.now());
//		pojo.setTime(LocalTime.of(10, 0));
//		pojo.setStatus(SchedulingStatus.MARCADO);
//		return pojo;
//	}
//
//	@Test
//	public void findAllTest() {
//		// when
//		List<Scheduling> list = this.service.findAll();
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
//		Scheduling pojo = this.newTestObject();
//		// when
//		pojo = this.service.save(pojo);
//		// then
//		assertNotNull(pojo.getId());
//	}
//
//	@Test
//	public void logicalExclusionTest() {
//		// given
//		Scheduling pojo = this.newTestObject();
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
//		Scheduling pojo = new Scheduling();
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
//		Scheduling pojo = this.service.save(this.newTestObject());
//		this.service.logicalExclusion(pojo.getId());
//		// when
//		List<Scheduling> list = this.service.findAllDeleted();
//		// then
//		assertTrue(list.contains(pojo));
//	}
//
//	@Test
//	public void restoreDeletedTest() {
//		// given
//		Scheduling pojo = this.service.save(this.newTestObject());
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
//		Scheduling pojo = new Scheduling();
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
//		Scheduling pojo = this.service.save(this.newTestObject());
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
//		Scheduling pojo = new Scheduling();
//		// when
//		Assertions.assertThrows(ServiceException.class, () -> {
//			this.service.permanentDestroy(pojo.getId());
//		}, MessageFactory.getMessage(Messages.record_not_found_at_recycle_bin));
//		// then
//		// throws ServiceException
//	}
//}
