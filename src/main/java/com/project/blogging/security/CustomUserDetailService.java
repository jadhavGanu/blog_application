package com.project.blogging.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.blogging.entities.Permission;
import com.project.blogging.entities.Role;
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

		Set<GrantedAuthority> authorities = new HashSet<>();

		// Add Roles
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName())); // ROLE_ADMIN
			// Add Permissions
			role.getPermissions().forEach(permission -> {
				authorities.add(new SimpleGrantedAuthority(permission.getPermissionName())); // user.create
			});
		});

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())) // Example: "ROLE_ADMIN"
				.collect(Collectors.toList());
	}

//	@Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        Set<GrantedAuthority> authorities = new HashSet<>();
//
//        // Add Roles
//        user.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority(role.getName())); // ROLE_ADMIN
//            // Add Permissions
//            role.getPermissions().forEach(permission -> {
//                authorities.add(new SimpleGrantedAuthority(permission.getName())); // user.create
//            });
//        });
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                authorities
//        );
//    }

//	 @Override
//	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//	        User user = userRepository.findByUsername(username)
//	                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//
//	        return new org.springframework.security.core.userdetails.User(
//	                user.getUsername(),
//	                user.getPassword(),
//	                getAuthorities(user.getRoles())
//	        );
//	    }
}
