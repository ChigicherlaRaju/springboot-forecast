package com.boot.forecast.filter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.forecast.filter.model.PathDetails;

/**
 * The Interface PathDetailsRepository.
 */
@Repository
public interface PathDetailsRepository extends JpaRepository<PathDetails, Long> {

	/**
	 * Find by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	List<PathDetails> findByUserId(String userId);

}