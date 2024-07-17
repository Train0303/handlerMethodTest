package org.example.handlermethodtest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

	private final JwtProvider jwtProvider;

	@GetMapping("/")
	public String test(@CustomResolver UserDto userDto) {
		log.info(userDto.toString());
		return "test";
	}

	@PostMapping("/jwt")
	public String makeJwt(@RequestBody UserDto requestDto, HttpServletResponse response) {
		String token = jwtProvider.generateToken(requestDto);
		response.setHeader("Authorization", "Bearer " + token);
		return token;
	}
}
