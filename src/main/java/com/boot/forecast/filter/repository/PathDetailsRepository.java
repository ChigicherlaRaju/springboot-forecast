package com.boot.forecast.filter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.forecast.filter.model.PathDetails;

@Repository
public interface PathDetailsRepository extends JpaRepository<PathDetails, Long> {

	List<PathDetails> findByUserId(String userId);

}