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
import com.sublimeprev.api.domain.ProcessStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "process_mother")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProcessMother extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 8529581610860260869L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String observation;
	private String requirements;
	private ProcessStatus status;
	@OneToOne
	private Mother mother;
	private LocalDate dateStart;

}
