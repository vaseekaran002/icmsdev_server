package com.perksoft.icms.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.perksoft.icms.contants.Constants;
import com.perksoft.icms.models.JwtBlacklist;
import com.perksoft.icms.repository.JwtTokenRepository;
import com.perksoft.icms.security.services.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtTokenService {

	@Value("${icms.app.jwtSecret}")
	private String jwtSecret;

	@Value("${icms.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	@Autowired
	private JwtTokenRepository jwtTokenRepository;

	public String generateToken(Authentication authentication) {
		final Map<String, Object> claims = new HashMap<>();
		final UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		final List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		claims.put(Constants.ROLES, roles);
		return generateToken(claims, userPrincipal.getUsername());
	}

	// retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public List<String> getRoles(String token) {
		return getClaimFromToken(token, claims -> (List) claims.get(Constants.ROLES));
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieving any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private String generateToken(Map<String, Object> claims, String subject) {
		final long now = System.currentTimeMillis();
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(now))
				.setExpiration(new Date(now + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	// validate token
	public Boolean validateToken(String token) {
		final String username = getUsernameFromToken(token);
		log.info("username from token===={}", username);
		final JwtBlacklist JwtBlacklist = jwtTokenRepository.findByToken(token);
		return username != null && !isTokenExpired(token) && JwtBlacklist == null;
	}

	public JwtBlacklist saveJwtBlacklist(JwtBlacklist jwtBlacklist) {
		return jwtTokenRepository.save(jwtBlacklist);
	}
}
