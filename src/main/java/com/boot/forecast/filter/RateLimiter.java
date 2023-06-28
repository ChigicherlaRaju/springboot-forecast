package com.boot.forecast.filter;

import java.io.IOException;

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
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// @formatter:off

/**
 * The Class RateLimiter.
 */
@Component
public class RateLimiter implements Filter {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(RateLimiter.class);

	/** The user repository. */
	@Autowired
	private CustomUserRepository userRepository;

	/** The rate limiter service. */
	@Autowired
	private RateLimiterService rateLimiterService;

	/**
	 * Do filter.
	 *
	 * @param req the req
	 * @param res the res
	 * @param chain the chain
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
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