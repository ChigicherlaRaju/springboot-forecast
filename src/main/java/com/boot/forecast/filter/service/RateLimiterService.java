package com.boot.forecast.filter.service;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.boot.forecast.filter.model.PathDetails;
import com.boot.forecast.filter.model.UserDto;
import com.boot.forecast.filter.repository.PathDetailsRepository;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

// @formatter:off

/**
 * The Class RateLimiterService.
 */
@Service
public class RateLimiterService {

	/** The bucket cache. */
	private final Map<String, Bucket> bucketCache = new ConcurrentHashMap<String, Bucket>();

	/** The user service. */
	@Autowired
	private UserService userService;

	/** The path details repository. */
	@Autowired
	private PathDetailsRepository pathDetailsRepository;

	/**
	 * Resolve bucket.
	 *
	 * @param userId the user id
	 * @param path the path
	 * @return the bucket
	 */
	public synchronized Bucket resolveBucket(final String userId, final String path) {
		String userPath = new StringBuilder(userId).append(path).toString();

		Bucket reqBucket = bucketCache.get(userPath);
		if (reqBucket != null) {
			return reqBucket;
		} else {
			bucketCache.put(userPath, newBucket(userId, path));
			return resolveBucket(userId, path);
		}
	}

	/**
	 * Delete if exists.
	 *
	 * @param userId the user id
	 * @param path the path
	 */
	public void deleteIfExists(final String userId, final String path) {
		bucketCache.remove(new StringBuilder(userId).append(path).toString());
	}

	/**
	 * New bucket.
	 *
	 * @param userId the user id
	 * @param path the path
	 * @return the bucket
	 */
	private Bucket newBucket(final String userId, final String path) {
		UserDto userDetails = userService.getUserDetails(userId);
		if (!CollectionUtils.isEmpty(userDetails.getPathDetails())) {
			Optional<PathDetails> pathInfo = userDetails.getPathDetails().stream()
					.filter(e -> e.getPathUrl().equalsIgnoreCase(path)).findFirst();
			if (pathInfo.isPresent()) {
				return Bucket.builder().addLimit(Bandwidth.classic(pathInfo.get().getMaxCount(),
						Refill.intervally(pathInfo.get().getMaxCount(), Duration.ofSeconds(1)))).build();
			}
		}
		pathDetailsRepository.save(PathDetails.builder().pathUrl(path).maxCount(1).userId(userId).build());
		return Bucket.builder().addLimit(Bandwidth.classic(1, Refill.intervally(1, Duration.ofSeconds(1)))).build();
	}

}

// @formatter:on