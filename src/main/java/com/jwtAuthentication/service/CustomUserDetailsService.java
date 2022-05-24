package com.jwtAuthentication.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
		
		if(userName.equals("Mansi")) {
			return new User("Mansi","Mansi123",new ArrayList<>());
		}
		else {
			throw new UsernameNotFoundException("User not found !!");
		}
		
	}

}
