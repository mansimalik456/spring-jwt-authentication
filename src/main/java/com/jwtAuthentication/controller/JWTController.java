package com.jwtAuthentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwtAuthentication.helper.JwtUtils;
import com.jwtAuthentication.model.JWTRequest;
import com.jwtAuthentication.model.JWTResponse;
import com.jwtAuthentication.service.CustomUserDetailsService;

@RestController
public class JWTController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public ResponseEntity<?> generateToken(@RequestBody JWTRequest jwtRequest) throws Exception{
		System.out.println(jwtRequest);
		
		try {
			
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
			
		}catch(UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("Bad Crendential");
		}catch(BadCredentialsException e) {
			e.printStackTrace();
			throw new Exception("Bad Crendential");
		}
		
		
		// fine area 
		
		UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUserName());
		String token = this.jwtUtils.generateToken(userDetails);
		
		System.out.println("JWT : " + token);
		
		return ResponseEntity.ok(new JWTResponse(token));
		
	}

}
