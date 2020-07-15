package com.sublimeprev.api.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sublimeprev.api.model.AddressMother;

import lombok.Data;

@Data
public class AddressMotherReqDTO {
	private Long id;
	@NotBlank
	private String street;
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
	
public AddressMother toEntity(AddressMotherReqDTO entity) {
		AddressMother addressMother = AddressMother.builder()
				.id(entity.getId())
				.street(entity.getStreet())
				.numberHouse(entity.getNumberHouse())
				.neighborhood(entity.getNeighborhood())
				.city(entity.getCity())
				.state(entity.getState())
				.complement(entity.getComplement())
				.zipcode(entity.getZipcode())
				.build();
		
		return addressMother;
	}
}
