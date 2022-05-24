package com.jwtAuthentication.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JWTRequest {

	String userName;
	String password;	
	
	
}
