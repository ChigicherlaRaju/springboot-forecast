package com.boot.forecast.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import com.boot.forecast.filter.model.CustomUser;
import com.boot.forecast.filter.repository.CustomUserRepository;
import com.boot.forecast.filter.service.RateLimiterService;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;

// @formatter:off

@Component
public class RateLimiter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(RateLimiter.class);

	@Autowired
	private CustomUserRepository userRepository;

	@Autowired
	private RateLimiterService rateLimiterService;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			String path = new UrlPathHelper().getPathWithinApplication(request);
			final CustomUser user = userRepository.findByUsername(authentication.getName()).get();
			final Bucket tokenBucket = rateLimiterService.resolveBucket(user.getUsername(), path);
			final ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);
			if (!probe.isConsumed()) {
				HttpServletResponse response = (HttpServletResponse) res;
				LOG.info("Exhausted limit. Hence sending back");
				response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests!!! Please try later");
			}
		}
		chain.doFilter(req, res);
	}

}

// @formatter:on