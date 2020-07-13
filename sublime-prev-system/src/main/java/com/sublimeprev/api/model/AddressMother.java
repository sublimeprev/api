package com.sublimeprev.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sublimeprev.api.bases.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address_mothers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AddressMother extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5395640503184170267L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String street;
	private int numberHouse;
	private String neighborhood;
	private String city;
	private String state;
	private String complement;
	private String zipcode;
	@OneToOne
	private Mother mother;
	
}
