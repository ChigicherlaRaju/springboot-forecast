package com.boot.forecast.filter.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonInclude(content = Include.NON_NULL)
public class UserDto {
	private CustomUser user;
	private List<PathDetails> pathDetails;

	public static UserDto getAllDetails(@NonNull CustomUser user, @NonNull List<PathDetails> pathDetails) {
		return UserDto.builder().user(user).pathDetails(pathDetails).build();
	}

}