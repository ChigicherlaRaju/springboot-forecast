package com.boot.forecast.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.AllArgsConstructor;
import lombok.Getter;

@ConfigurationProperties(prefix = "openapi")
@ConstructorBinding
@AllArgsConstructor
@Getter
public class OpenApiProperties {
	private final String projectTitle;
	private final String projectDescription;
	private final String projectVersion;
}
