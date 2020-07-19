package com.sublimeprev.api.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sublimeprev.api.bases.BaseEntity;
import com.sublimeprev.api.domain.ReasonForDismissal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "last_company")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LastCompany extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4182490117958830873L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private boolean unemploymentInsurance;
	private ReasonForDismissal reasonForDismissal;
	private LocalDate dismissalDate;
	private LocalDate admissionDate;
	@OneToOne
	private Mother mother;
}
