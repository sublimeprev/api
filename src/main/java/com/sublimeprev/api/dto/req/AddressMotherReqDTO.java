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

	public AddressMother toEntity(AddressMother entity) {
		entity.setId(this.id);
		entity.setStreet(this.street);
		entity.setNumberHouse(this.numberHouse);
		entity.setNeighborhood(this.neighborhood);
		entity.setCity(this.city);
		entity.setComplement(this.complement);
		entity.setState(this.state);
		entity.setZipcode(this.zipcode);
		return entity;
	}
}
