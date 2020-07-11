package com.sublimeprev.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;

import com.sublimeprev.api.bases.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trainings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Training extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6026747231401554032L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@ManyToOne
	private User user;
	private String fileKey;
	private String mimetype;
	private long size;
	@Transient
	private String encodedContent;

	@Transient
	public MediaType getMediaType() {
		MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
		try {
			mediaType = MediaType.parseMediaType(this.mimetype);
		} catch (Exception e) {
		}
		return mediaType;
	}
}