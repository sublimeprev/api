package com.sublimeprev.api.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import com.sublimeprev.api.bases.BaseEntity;
import com.sublimeprev.api.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5300854973889458160L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Column(unique = true)
	private String username;
	private String encryptedPassword;
	@NotBlank
	private String email;
	@NotBlank
	private String name;
	private String city;
	private String phone;
	private LocalDate birthdate;
	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.ORDINAL)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role_id")
	private Set<Role> roles;
	@Transient
	private String newPassword;
	private String comments;
}