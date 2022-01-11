package com.perksoft.icms.config;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.perksoft.icms.contants.Constants;
import com.perksoft.icms.security.services.UserDetailsServiceImpl;
import com.perksoft.icms.service.JwtTokenService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenService jwtTokenService;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final Optional<String> jwt = parseJwtFromRequest(request);

		jwt.ifPresent(token -> {

			try {

				if (jwtTokenService.validateToken(token)) {
					String username = jwtTokenService.getUsernameFromToken(token);

					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			} catch (Exception e) {
				log.error("Cannot set user authentication: {}", e);
			}
		});

		filterChain.doFilter(request, response);
	}

	private Optional<String> parseJwtFromRequest(HttpServletRequest request) {
		String headerAuth = request.getHeader(Constants.AUTHORIZATION);

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(Constants.BEARER)) {
			return Optional.of(jwtTokenService.decryptToken(headerAuth.substring(7)));
		}

		return Optional.empty();
	}
}
