package com.boot.forecast.filter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PATH_COUNT_DETAILS")
public class PathDetails {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "API_ID")
	@JsonIgnore
	private Long id;

	/** The path URL. */
	@Column(name = "PATH_URL", nullable = false)
	private String pathUrl;

	/** The max count. */
	@Column(name = "MAX_COUNT", nullable = false)
	private int maxCount;

	/** The user id. */
	@Column(name = "USER_NAME", nullable = false)
	private String userId;

}