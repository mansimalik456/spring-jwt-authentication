package com.jwtAuthentication.config;

import java.io.IOException;

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
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwtAuthentication.helper.JwtUtils;
import com.jwtAuthentication.service.CustomUserDetailsService;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		
		// get jwt
		// Bearer
		// validate
		
		String requestTokenHeader = request.getHeader("Authorization");
		String userName = null;
		String jwtToken = null;
		
		//null and format
		if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken  = requestTokenHeader.substring(7);
			
			try {
				
				userName = this.jwtUtils.extractUsername(jwtToken);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(userName);
			
			if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}
			else {
				System.out.println("Token is not validated ");
			}
			
			filterChain.doFilter(request, response);
			
		}
		
		
	}
	
}
