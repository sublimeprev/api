//package com.cesararaujostudio.api.service;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.time.DayOfWeek;
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
//import com.cesararaujostudio.api.model.AgendaConfig;
//import com.cesararaujostudio.api.service.AgendaConfigService;
//
//@ActiveProfiles(profiles = "test")
//@SpringBootTest
//public class AgendaConfigServiceTest {
//
//	@Autowired
//	private AgendaConfigService service;
//
//	private AgendaConfig newTestObject() {
//		AgendaConfig pojo = new AgendaConfig();
//		pojo.setDayOfWeek(DayOfWeek.MONDAY);
//		pojo.setTime(LocalTime.now());
//		pojo.setMaxLimit(10);
//		return pojo;
//	}
//
//	@Test
//	public void findAllTest() {
//		// when
//		List<AgendaConfig> list = this.service.findAll();
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
//		AgendaConfig pojo = this.newTestObject();
//		// when
//		pojo = this.service.save(pojo);
//		// then
//		assertNotNull(pojo.getId());
//	}
//
//	@Test
//	public void logicalExclusionTest() {
//		// given
//		AgendaConfig pojo = this.newTestObject();
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
//		AgendaConfig pojo = new AgendaConfig();
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
//		AgendaConfig pojo = this.service.save(this.newTestObject());
//		this.service.logicalExclusion(pojo.getId());
//		// when
//		List<AgendaConfig> list = this.service.findAllDeleted();
//		// then
//		assertTrue(list.contains(pojo));
//	}
//
//	@Test
//	public void restoreDeletedTest() {
//		// given
//		AgendaConfig pojo = this.service.save(this.newTestObject());
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
//		AgendaConfig pojo = new AgendaConfig();
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
//		AgendaConfig pojo = this.service.save(this.newTestObject());
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
//		AgendaConfig pojo = new AgendaConfig();
//		// when
//		Assertions.assertThrows(ServiceException.class, () -> {
//			this.service.permanentDestroy(pojo.getId());
//		}, MessageFactory.getMessage(Messages.record_not_found_at_recycle_bin));
//		// then
//		// throws ServiceException
//	}
//}
