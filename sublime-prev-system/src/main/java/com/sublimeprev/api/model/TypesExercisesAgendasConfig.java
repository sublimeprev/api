package com.sublimeprev.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "types_exercises_agendas_config")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TypesExercisesAgendasConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 551285850639706632L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "type_exercise_id")
	private Long typeExerciseId;
	
	@Column(name = "agenda_config_id")
	private Long agendaConfigId;
	
	@ManyToOne
	@JoinColumn(name = "type_exercise_id", insertable = false, updatable = false)
	private TypeExercise typeExercise;

	@ManyToOne
	@JoinColumn(name = "agenda_config_id", insertable = false, updatable = false)
	private AgendaConfig agendaConfig;

	public TypesExercisesAgendasConfig(Long typeExerciseId, Long agendaConfigId) {
		this.typeExerciseId = typeExerciseId;
		this.agendaConfigId = agendaConfigId;
	}	
}

