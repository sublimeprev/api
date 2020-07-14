package com.sublimeprev.api.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sublimeprev.api.model.AddressMother;

import lombok.Data;

@Data
public class AddressMotherReqDTO {
	@NotBlank
	private String street;
	@NotBlank
	private int numberHouse;
	@NotBlank
	private String neighborhood;
	@NotBlank
	private String city;
	@NotBlank
	private String state;
	private String complement;
	private String zipcode;
	@NotNull
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
