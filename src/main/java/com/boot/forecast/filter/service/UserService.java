package com.boot.forecast.filter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.boot.forecast.filter.model.CustomUser;
import com.boot.forecast.filter.model.PathDetails;
import com.boot.forecast.filter.model.UserDto;
import com.boot.forecast.filter.repository.CustomUserRepository;
import com.boot.forecast.filter.repository.PathDetailsRepository;

// @formatter:off

/**
 * The Class UserService.
 */
@Service
public class UserService {

	/** The user repository. */
	@Autowired
	private CustomUserRepository userRepository;

	/** The path details repository. */
	@Autowired
	private PathDetailsRepository pathDetailsRepository;

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public List<CustomUser> getUsers() {
		return userRepository.findAll();
	}

	/**
	 * Gets the paths details.
	 *
	 * @return the paths details
	 */
	public List<PathDetails> getPathsDetails() {
		return pathDetailsRepository.findAll();
	}

	/**
	 * Gets the user details.
	 *
	 * @param username the user name
	 * @return the user details
	 * @throws UsernameNotFoundException the {@link UsernameNotFoundException}
	 */
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