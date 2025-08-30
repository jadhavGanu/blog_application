package com.project.blogging.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blogging.payload.ApiResponse;
import com.project.blogging.payload.UserDto;
import com.project.blogging.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	// POST- create user
//	@PreAuthorize("hasAuthority('user.create')")
	@PreAuthorize("hasPermission(null, 'user.create')")
//	@PreAuthorize("hasPermission(#request,'{user.create} || {spendmanagement.account.ALL}')")
	@PostMapping("/create")
	public ResponseEntity<UserDto> createUser( @Valid @RequestBody UserDto userDto) {
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

	// GET- get user
//	@PreAuthorize("hasAuthority('user.read')")
	@PreAuthorize("hasPermission(null, 'user.read')")
//	@PreAuthorize("hasPermission(#request,'{user.read} || {spendmanagement.account.ALL}')")
	@Cacheable(value = "userCache", key = "#user_id")
	@GetMapping("/{user_id}")
	public ResponseEntity<UserDto> getUserByUserId(@PathVariable("user_id") Integer user_id, Authentication authentication) {
		System.out.println("Getting Data From Database++++++++++++++++++++++");
		UserDto getUserDto = this.userService.getUserById(user_id);
		 System.out.println("User Authorities: " + authentication.getAuthorities());
		 return ResponseEntity.ok(getUserDto);
	}

	// GET- get list of users
	@Cacheable(value = "getAllUsersCache", key = "'allUsers'")
	@PreAuthorize("hasAuthority('user.read')")
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getUsers() {
		
		System.out.println("get all userss++++++++++++++++++_____________________");
		System.out.println("get{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{[[[");
		List<UserDto> getUsers = this.userService.getAllUsers();
		return new ResponseEntity<>(getUsers, HttpStatus.OK);
	}

	// UPDATE- update user
	@PreAuthorize("hasAuthority('user.update')")
	@PutMapping("/updateUser/{user_id}")
	public ResponseEntity<UserDto> updateUser( @Valid @RequestBody UserDto userDto, @PathVariable("user_id") Integer user_id) {
		UserDto updatedUserDto = this.userService.updateUser(userDto, user_id);
		return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
	}

	// DELETE- delete user
	@PreAuthorize("hasAuthority('user.delete')")
//	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{user_id}")
	public ResponseEntity<?> deleteUser(@PathVariable("user_id") Integer user_id) {
		this.userService.deleteUser(user_id);
		return new ResponseEntity<>(new ApiResponse("User deleted successfully...", true), HttpStatus.OK);
	}

}
