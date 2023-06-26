package com.boot.forecast.filter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boot.forecast.filter.model.CustomUser;
import com.boot.forecast.filter.repository.CustomUserRepository;

// @formatter:off

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private CustomUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Bad Credentials"));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), passwordEncoder.encode(user.getPassword()), List.of());
	}

}

// @formatter:on