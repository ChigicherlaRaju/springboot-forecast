package com.boot.forecast.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * The Class OpenApiConfig.
 */
@Configuration
public class OpenApiConfig {

	/** The Constant SCHEME_NAME. */
	private static final String SCHEME_NAME = "basicAuth";

	/** The Constant SCHEME. */
	private static final String SCHEME = "basic";

	/**
	 * Custom open API.
	 *
	 * @param properties the properties
	 * @return the open API
	 */
	@Bean
	OpenAPI customOpenAPI(OpenApiProperties properties) {
		return new OpenAPI().info(getInfo(properties))
				.components(new Components().addSecuritySchemes(SCHEME_NAME, createSecurityScheme()))
				.addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME));
	}

	/**
	 * Gets the info.
	 *
	 * @param properties the properties
	 * @return the info
	 */
	private Info getInfo(OpenApiProperties properties) {
		return new Info().title(properties.getProjectTitle()).description(properties.getProjectDescription())
				.version(properties.getProjectVersion()).license(getLicense());
	}

	/**
	 * Gets the license.
	 *
	 * @return the license
	 */
	private License getLicense() {
		return new License().name("Unlicense").url("https://unlicense.org/");
	}

	/**
	 * Creates the security scheme.
	 *
	 * @return the security scheme
	 */
	private SecurityScheme createSecurityScheme() {
		return new SecurityScheme().name(SCHEME_NAME).type(SecurityScheme.Type.HTTP).scheme(SCHEME);
	}

}