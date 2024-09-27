package com.project.blogging.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.project.blogging.security.CustomUserDetailService;
import com.project.blogging.security.JwtAunthenticationFilter;
import com.project.blogging.security.JwtAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity(prePostEnabled=true)
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAunthenticationFilter jwtAunthenticationFilter;
	
//	 @SuppressWarnings("removal")
//	@Bean
//	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	              http
//	             .csrf()
//	             .disable()
//	             .authorizeHttpRequests()// athorized reuqusts
//	             .anyRequest()// all request
//	             .authenticated()// need to authenticated
//	             .and()// and
//	             .exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
//	             .and()
//	             .sessionManagement()
//	             .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//	              http.addFilterBefore(this.jwtAunthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//	        return http.build();
//	    }
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.csrf().disable() // Disable CSRF protection
	        .authorizeHttpRequests()
	            .requestMatchers("/api/v1/auth/**").permitAll()
	            .requestMatchers("/v3/api-docs/**").permitAll() 
	            .requestMatchers(HttpMethod.GET).permitAll()// Allow public access to login endpoint
	            .anyRequest().authenticated() // All other requests require authentication
	            .and()
	        .exceptionHandling()
	            .authenticationEntryPoint(this.jwtAuthenticationEntryPoint) // Handle unauthorized requests
	            .and()
	        .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Use stateless sessions

	    // Add JWT authentication filter before the UsernamePasswordAuthenticationFilter
	    http.addFilterBefore(this.jwtAunthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	    
	    return http.build();
	}


	 
		@Bean
		public DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
			authProvider.setUserDetailsService(customUserDetailService);
			authProvider.setPasswordEncoder(passwordEncoder());
			return authProvider;
		}

		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Bean
		public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
			AuthenticationManagerBuilder authenticationManagerBuilder = http
					.getSharedObject(AuthenticationManagerBuilder.class);

			authenticationManagerBuilder.authenticationProvider(authenticationProvider());

			return authenticationManagerBuilder.build();
		}
	 
	 
}
