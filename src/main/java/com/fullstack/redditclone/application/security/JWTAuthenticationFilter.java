package com.fullstack.redditclone.application.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	JWTProvider tokenProvider;
	
	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String JWT = getJWTFromRequest(request);
		
		if(StringUtils.hasText(JWT) && tokenProvider.validateToken(JWT)) {
			
			String userName = tokenProvider.getUserNameFromJWT(JWT);
			
			System.out.println("doFilterInternal "+userName);
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
			
			System.out.println("doFilterInternal "+userDetails);
			
			UsernamePasswordAuthenticationToken userNamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(null, userDetails);
			
			userNamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(userNamePasswordAuthenticationToken);
		}
		
		filterChain.doFilter(request, response);
		
	}

	private String getJWTFromRequest(HttpServletRequest request) {
		
		String bearerToken = request.getHeader("Authorization");
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
			System.out.println(bearerToken);
			return bearerToken.substring(7);
		}
		
		return bearerToken;
		
	}

}
