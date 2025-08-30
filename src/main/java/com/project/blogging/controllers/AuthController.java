package com.project.blogging.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blogging.exception.InvalidUserNameOrPasswordException;
import com.project.blogging.payload.JwtAuthRequest;
import com.project.blogging.payload.JwtAuthResponse;
import com.project.blogging.payload.UserDto;
import com.project.blogging.security.JwtTokenHelper;
import com.project.blogging.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		
		this.authenticate(request.getUsername(), request.getPassword());

		UserDetails userDtails = this.userDetailsService.loadUserByUsername(request.getUsername());

		String token = this.jwtTokenHelper.generateToken(userDtails);

		JwtAuthResponse response = new JwtAuthResponse();;
		response.setToken(token);

		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}


	private void authenticate(String username, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(username,password);
		
		try {
	
		this.authenticationManager.authenticate(authenticationToken);
		}catch(BadCredentialsException e)
		{
			System.out.println("Invalid Details !!");
			throw new InvalidUserNameOrPasswordException("Invalid usrname or password", password);
		}
		
	}
	
	// register new user api
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
		UserDto registerDto = this.userService.registerNewUser(userDto);

		return new ResponseEntity<>(registerDto, HttpStatus.CREATED);
	}
	
	@GetMapping("/test")
	public ResponseEntity<String> test() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    System.out.println("Authenticated User: " + authentication.getName());
	    System.out.println("User Authorities: " + authentication.getAuthorities());

	    return ResponseEntity.ok("Access granted!");
	}

}
