package com.sublimeprev.api.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.sublimeprev.api.bases.BaseEntity;
import com.sublimeprev.api.domain.MaritalStatus;
import com.sublimeprev.api.domain.Schooling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mothers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Mother extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 2268648383823387705L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String email;
	@NotBlank
	private String name;
	private String phone;
	private LocalDate birthdate;
	@NotBlank
	private String cpf;
	private String rg;
	private String pis;
	private MaritalStatus maritalStatus;
	private Schooling schooling;
	private String fatherName;
	private String motherName;
	

}
