package com.boot.forecast.filter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.boot.forecast.filter.model.PathDetails;
import com.boot.forecast.filter.model.CustomUser;
import com.boot.forecast.filter.model.UserDto;
import com.boot.forecast.filter.repository.PathDetailsRepository;
import com.boot.forecast.filter.repository.CustomUserRepository;

// @formatter:off

@Service
public class UserService {

	@Autowired
	private CustomUserRepository userRepository;

	@Autowired
	private PathDetailsRepository pathDetailsRepository;

	public List<CustomUser> getUsers() {
		return userRepository.findAll();
	}

	public List<PathDetails> getPathsDetails() {
		return pathDetailsRepository.findAll();
	}

	public UserDto getUserDetails(String username) throws UsernameNotFoundException {
		Optional<CustomUser> userDetails = userRepository.findByUsername(username);
		if (userDetails.isPresent()) {
			List<PathDetails> pathDetails = pathDetailsRepository.findByUserId(userDetails.get().getUsername());
			return UserDto.getAllDetails(userDetails.get(), CollectionUtils.isEmpty(pathDetails) ? List.of() : pathDetails);
		} else {
			throw new UsernameNotFoundException("Username: " + username + " not found!");
		}
	}
}

// @formatter:on