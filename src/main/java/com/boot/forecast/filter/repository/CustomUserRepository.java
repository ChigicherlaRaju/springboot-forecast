package com.boot.forecast.filter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.forecast.filter.model.CustomUser;

/**
 * The Interface CustomUserRepository.
 */
@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {

	/**
	 * Find by user name.
	 *
	 * @param username the user name
	 * @return the optional
	 */
	Optional<CustomUser> findByUsername(String username);

}