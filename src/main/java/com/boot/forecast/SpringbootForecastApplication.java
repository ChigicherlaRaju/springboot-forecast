package com.boot.forecast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import com.boot.forecast.filter.model.CustomUser;
import com.boot.forecast.filter.model.PathDetails;
import com.boot.forecast.filter.repository.CustomUserRepository;
import com.boot.forecast.filter.repository.PathDetailsRepository;

import jakarta.annotation.PostConstruct;

// @formatter:off

/**
 * The Class SpringbootForecastApplication.
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringbootForecastApplication {
	
	/** The path details repository. */
	@Autowired
	private PathDetailsRepository pathDetailsRepository;
	
	/** The user repository. */
	@Autowired
	private CustomUserRepository userRepository;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(SpringbootForecastApplication.class, args);
	}
	
	/**
	 * Load data.
	 */
	@PostConstruct
	public void loadData() {
		userRepository.save(CustomUser.builder().username("user1").password("password").build());
		userRepository.save(CustomUser.builder().username("user2").password("password").build());

		pathDetailsRepository.save(PathDetails.builder().pathUrl("/api/v1/developers").maxCount(5).userId("user1").build());
		pathDetailsRepository.save(PathDetails.builder().pathUrl("/api/v1/organizations").maxCount(2).userId("user1").build());
		pathDetailsRepository.save(PathDetails.builder().pathUrl("/api/v1/developers").maxCount(3).userId("user2").build());
		pathDetailsRepository.save(PathDetails.builder().pathUrl("/api/v1/organizations").maxCount(5).userId("user2").build());
	}

}

// @formatter:on