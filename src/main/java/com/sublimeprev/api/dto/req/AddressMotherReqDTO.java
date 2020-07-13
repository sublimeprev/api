package com.sublimeprev.api.dto.req;

import com.sublimeprev.api.model.AddressMother;

import lombok.Data;

@Data
public class AddressMotherReqDTO {
	private String street;
	private int numberHouse;
	private String neighborhood;
	private String city;
	private String state;
	private String complement;
	private String zipcode;
	private Long idMother;
	
public AddressMother toEntity(AddressMother entity) {
		this.setStreet(entity.getStreet());
		this.setNumberHouse(entity.getNumberHouse());
		this.setNeighborhood(entity.getNeighborhood());
		this.setCity(entity.getCity());
		this.setState(entity.getState());
		this.setComplement(entity.getComplement());
		this.setZipcode(entity.getZipcode());
		return entity;
	}
}
