package com.boot.forecast.filter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.forecast.filter.model.CustomUser;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
	
	Optional<CustomUser> findByUsername(String username);
	
}