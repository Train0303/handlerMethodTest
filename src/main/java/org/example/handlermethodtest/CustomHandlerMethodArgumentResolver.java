package org.example.handlermethodtest;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class CustomHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Value("${token.secret}")
	private String secret;

	static final String TOKEN_HEADER = "Authorization";

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(UserDto.class);
	}

	@Override
	public UserDto resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String token = webRequest.getHeader(TOKEN_HEADER);

		if (token == null || !token.startsWith("Bearer ")) {
			throw new Exception("토큰 입력 부탁");
		}

		return parseUserDto(token.substring(7));
	}

	private UserDto parseUserDto(String token) throws Exception {
		try{
			byte[] secretKeyBytes = Base64.getEncoder().encode(secret.getBytes());
			SecretKey key = Keys.hmacShaKeyFor(secretKeyBytes);
			Claims claims = Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();

			return UserDto.builder()
				.id(Long.parseLong(claims.getId()))
				.email(claims.get("email", String.class))
				.build();
		} catch (JwtException e){
			throw new Exception("jwtError");
		}
	}
}

