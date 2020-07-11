package com.sublimeprev.api.model;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.sublimeprev.api.bases.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agenda_configs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AgendaConfig extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8855781863430825942L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private DayOfWeek dayOfWeek;
	
	@NotNull
	private LocalTime time;
	
	private Integer maxLimit;
	
	@ManyToMany(mappedBy = "agendasConfigs", fetch = FetchType.EAGER)
	private List<TypeExercise> typesExercises;
	
	@Override
	public String toString() {
		return String.format("%d - %s %s", id, dayOfWeek.toString(), time.toString());
	}
}