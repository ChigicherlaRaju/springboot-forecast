package com.boot.forecast.filter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "API_ID")
	@JsonIgnore
	private Long id;

	@Column(name = "PATH_URL", nullable = false)
	private String pathUrl;

	@Column(name = "MAX_COUNT", nullable = false)
	private int maxCount;
	
	@Column(name = "USER_NAME", nullable = false)
	private String userId;

}