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
@Table(name = "users_agendas_configs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserAgendaConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6252385253502979282L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "agenda_config_id")
	private Long agendaConfigId;
	
	private Long typeExerciseId;

	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "agenda_config_id", insertable = false, updatable = false)
	private AgendaConfig agendaConfig;

	public UserAgendaConfig(Long agendaConfigId, Long userId, Long typeExerciseId) {
		this.agendaConfigId = agendaConfigId;
		this.userId = userId;
		this.typeExerciseId = typeExerciseId;
	}
}
