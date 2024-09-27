package com.project.blogging.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.blogging.entities.User;
import com.project.blogging.exception.ResourceNotFoundException;
import com.project.blogging.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// loading/fetching user from database by username

		User user = this.userRepo.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("user", "email : " + username, 0));

		return user;
	}
 
}
