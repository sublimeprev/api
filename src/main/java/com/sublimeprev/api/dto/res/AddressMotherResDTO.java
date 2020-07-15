package com.sublimeprev.api.dto.res;

import java.time.LocalDateTime;

import com.sublimeprev.api.model.AddressMother;

import lombok.Data;

@Data
public class AddressMotherResDTO {
	private Long id;
	private boolean deleted;
	private String createdBy;
	private String updatedBy;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String street;
	private int numberHouse;
	private String neighborhood;
	private String city;
	private String state;
	private String complement;
	private String zipcode;
	private Long idMother;
	
	private AddressMotherResDTO(AddressMother entity) {
		this.id = entity.getId();
		this.deleted = entity.isDeleted();
		this.createdBy = entity.getCreatedBy();
		this.updatedBy = entity.getUpdatedBy();
		this.createdAt = entity.getCreatedAt();
		this.updatedAt = entity.getUpdatedAt();
		this.setStreet(entity.getStreet());
		this.setNumberHouse(entity.getNumberHouse());
		this.setNeighborhood(entity.getNeighborhood());
		this.setCity(entity.getCity());
		this.setState(entity.getState());
		this.setComplement(entity.getComplement());
		this.setZipcode(entity.getZipcode());
		this.setIdMother(entity.getMother().getId());
	}

	public static AddressMotherResDTO of(AddressMother entity) {
		return entity == null ? null : new AddressMotherResDTO(entity);
	}

}
