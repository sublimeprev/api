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
@Table(name = "types_exercises_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TypesExerciseUser implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -6543048009067409397L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "type_exercise_id")
	private Long typeExerciseId;
	
	@Column(name = "user_id")
	private Long userId;

	@ManyToOne
	@JoinColumn(name = "type_exercise_id", insertable = false, updatable = false)
	private TypeExercise typeExercise;

	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	public TypesExerciseUser(Long typeExerciseId, Long userId) {
		this.typeExerciseId = typeExerciseId;
		this.userId = userId;
	}
}
