package org.example.handlermethodtest;

import lombok.Builder;

public record UserDto(
	Long id,
	String email
) {
	@Builder
	public UserDto {
	}
}
