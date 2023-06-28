package com.boot.forecast.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import com.boot.forecast.filter.RateLimiter;

/**
 * The Class ForecastConfig.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class ForecastConfig {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(ForecastConfig.class);

	/** The Constant SWAGGER_WHITELIST. */
	private static final String[] SWAGGER_WHITELIST = { "/v2/api-docs", "/v3/api-docs/**",
			"/swagger-resources/configuration/ui", "/swagger-resources", "/h2-console", "/h2-console/**",
			"/swagger-resources/configuration/security", "/swagger-ui.html", "/swagger-ui/**", "/webjars/**",
			"/configuration/**", "/swagger*/**", "/health-check", "/actuator/**", "/login", "/sign-up" };

	/** The rate limiter. */
	@Autowired
	private RateLimiter rateLimiter;

	/**
	 * Rest template.
	 *
	 * @param builder the {@link RestTemplateBuilder}
	 * @return the {@link RestTemplate}
	 */
	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		LOG.info("Inside RestTemplate builder method!!!");
		return builder.build();
	}

	/**
	 * Filter chain.
	 *
	 * @param http the HTTP
	 * @return the security filter chain
	 * @throws Exception the exception
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests((authz) -> authz.requestMatchers(SWAGGER_WHITELIST).permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterAfter(rateLimiter, UsernamePasswordAuthenticationFilter.class)
				.httpBasic(Customizer.withDefaults());

		return http.build();
	}

	/**
	 * Web security customizer.
	 *
	 * @return the web security customizer
	 */
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(SWAGGER_WHITELIST);
	}

	/**
	 * Password encoder.
	 *
	 * @return the password encoder
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
