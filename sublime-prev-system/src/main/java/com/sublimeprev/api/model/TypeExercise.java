package com.sublimeprev.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.sublimeprev.api.bases.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "type_exercises")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "agendasConfigs", "users"})
@EqualsAndHashCode(callSuper = false)
public class TypeExercise extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7167393416350509467L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String type;
	
	private Boolean active;
	
	@ManyToMany
	@JoinTable(name = "types_exercises_agendas_config",
			joinColumns = @JoinColumn(name = "type_exercise_id"),
			inverseJoinColumns = @JoinColumn(name = "agenda_config_id"))
	private List<AgendaConfig> agendasConfigs;
	
	@ManyToMany
//	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	@JoinTable(name = "types_exercises_user",
			joinColumns = @JoinColumn(name = "type_exercise_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;
	
}
