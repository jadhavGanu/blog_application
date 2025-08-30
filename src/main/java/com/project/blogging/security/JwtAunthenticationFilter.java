package com.project.blogging.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// step 3
@Component
public class JwtAunthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// get token

		String requestToken = request.getHeader("Authorization");

		// Bearer 23456754edfa

		System.out.println(requestToken);

		String username = null;

		String token = null;

		if (requestToken != null && requestToken.startsWith("Bearer")) {

			token = requestToken.substring(7);
			try {
				username = this.jwtTokenHelper.extractUsername(token);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get jwt token");
			} catch (ExpiredJwtException e) {
				System.out.println("Unable to get jwt token");
			} catch (MalformedJwtException e) {
				System.out.println("Unable to get jwt token");
			}

		} else {
			System.out.println("Jwt does not begin with Bearer");
		}

		// once we get token then , we can validate
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			if (this.jwtTokenHelper.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails,null, userDetails.getAuthorities());

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				System.out.println("Invalid jwt token");
			}
		} else {
			System.out.println("username is null or context is not null");
		}

		filterChain.doFilter(request, response);
	}
	
//	 @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        String requestToken = request.getHeader("Authorization");
//        String username = null;
//        String token = null;
//
//        if (requestToken != null && requestToken.startsWith("Bearer ")) {
//            token = requestToken.substring(7);
//            
//            try {
//                username = jwtTokenHelper.extractUsername(token);
//            } catch (ExpiredJwtException e) {
//                handleException(response, "JWT Token has expired.");
//                return;
//            } catch (MalformedJwtException e) {
//                handleException(response, "Invalid JWT Token format.");
//                return;
//            } catch (SignatureException e) {
//                handleException(response, "Invalid JWT token: Signature does not match!");
//                return;
//            } catch (IllegalArgumentException e) {
//                handleException(response, "JWT Token is missing or incorrect.");
//                return;
//            }
//        } else {
//            System.out.println("JWT does not begin with Bearer.");
//        	 handleException(response, "JWT does not begin with Bearer.");
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//            if (jwtTokenHelper.validateToken(token, userDetails)) {
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            } else {
//                handleException(response, "Invalid JWT Token.");
//                return;
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    // Send proper JSON error response
//    private void handleException(HttpServletResponse response, String message) throws IOException {
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
//        response.setContentType("application/json");
//        PrintWriter writer = response.getWriter();
//        writer.println("{ \"error\": \"" + message + "\" }");
//    }


}
