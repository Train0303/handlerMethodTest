package org.example.handlermethodtest;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	@Value("${token.secret}")
	private String secret;

	public String generateToken(UserDto userDto) {
		byte[] secretKeyBytes = Base64.getEncoder().encode(secret.getBytes());
		SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
		Instant now = Instant.now();
		return Jwts.builder()
			.id(String.valueOf(userDto.id()))
			.expiration(Date.from(now.plusSeconds(1000))) // 1000초
			.issuedAt(Date.from(now))
			.claim("email", userDto.email())
			.signWith(secretKey)
			.compact();
	}
}
