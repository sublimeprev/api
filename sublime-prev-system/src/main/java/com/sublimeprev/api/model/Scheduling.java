package com.sublimeprev.api.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.sublimeprev.api.bases.BaseEntity;
import com.sublimeprev.api.domain.SchedulingStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "schedulings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Scheduling extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4023914415020692360L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@ManyToOne
	private User user;
	@NotNull
	private LocalDate date;
	@NotNull
	private LocalTime time;
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private SchedulingStatus status;
	@ManyToOne
	private TypeExercise typeExercise;
}