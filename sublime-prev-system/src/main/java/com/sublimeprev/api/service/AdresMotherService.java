package com.sublimeprev.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sublimeprev.api.config.i18n.ServiceException;
import com.sublimeprev.api.model.AddresMother;
import com.sublimeprev.api.model.Mother;
import com.sublimeprev.api.repository.AddressMotherRepository;

@Service
public class AdresMotherService {
	@Autowired
	private AddressMotherRepository repository;
	
	@Autowired
	private MotherService motherService;
	
	public AddresMother saveAddresMother(AddresMother addresMother, Long idMother) {
		Mother mother = this.motherService.findById(idMother);
		addresMother.setMother(mother);
		return this.repository.save(addresMother);
	}
	
	public AddresMother updateAddresMother(AddresMother addresMother) {
		AddresMother address = this.findById(addresMother.getId());
		
		AddresMother newAddress = AddresMother.builder()
				.city(address.getCity())
				.complement(address.getComplement())
				.neighborhood(address.getNeighborhood())
				.numberHouse(address.getNumberHouse())
				.state(address.getState())
				.street(address.getStreet())
				.zipcode(address.getZipcode())
				.build();
		return this.repository.save(newAddress);
	}
	
	public AddresMother findById(Long idAddresMother) {
		return this.repository.findById(idAddresMother).orElseThrow(() -> new ServiceException("Endereço não encontrado."));
	}
	
}
