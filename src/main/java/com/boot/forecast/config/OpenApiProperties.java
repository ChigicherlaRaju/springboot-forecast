package com.boot.forecast.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Class OpenApiProperties.
 */

@ConfigurationProperties(prefix = "openapi")
@AllArgsConstructor
@Getter
public class OpenApiProperties {

	/** The project title. */
	private final String projectTitle;

	/** The project description. */
	private final String projectDescription;

	/** The project version. */
	private final String projectVersion;
	
}