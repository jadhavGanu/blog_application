package com.project.blogging.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.blogging.entities.Role;
import com.project.blogging.entities.User;
import com.project.blogging.exception.ResourceNotFoundException;
import com.project.blogging.payload.AppConstants;
import com.project.blogging.payload.UserDto;
import com.project.blogging.repositories.RoleRepo;
import com.project.blogging.repositories.UserRepo;
import com.project.blogging.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {

//		User user = this.dtoToUser(userDto);

		User user = this.modelMapper.map(userDto, User.class);

		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		user.setIsAdmin(userDto.getIsAdmin());

		Role role = null;

		if (user.getIsAdmin()) {
			role = this.roleRepo.findById(AppConstants.ADMIN_USER).get();
		} else {
			role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		}

		user.getRoles().add(role);

		User saveUser = this.userRepo.save(user);

//		UserDto saveUserDto = this.userToDto(saveUser);

		UserDto saveUserDto = this.modelMapper.map(saveUser, UserDto.class);

		return saveUserDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		user.setAbout(userDto.getAbout());
		
		user.setIsAdmin(userDto.getIsAdmin());

		Role role = null;

		if (user.getIsAdmin()) {
			role = this.roleRepo.findById(AppConstants.ADMIN_USER).get();
		} else {
			role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		}

		user.getRoles().add(role);

		User updateUser = this.userRepo.save(user);

//		UserDto updateUserDto = this.userToDto(updateUser);

		UserDto updateUserDto = this.modelMapper.map(updateUser, UserDto.class);

		return updateUserDto;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
		
		System.out.println(user.getAuthorities());

//		UserDto getUserDto = this.userToDto(user);

		UserDto getUserDto = this.modelMapper.map(user, UserDto.class);

		return getUserDto;
	}

	@Override
	public List<UserDto> getAllUsers() {

		List<User> users = this.userRepo.findAll();

		List<UserDto> listOfUserDto = users.stream().map(user -> this.modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());

		return listOfUserDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
		user.getRoles().clear();
		this.userRepo.delete(user);
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		// we have encoded password
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

//		user.setIsAdmin(userDto.getIsAdmin());

		Role role = null;

		if (user.getIsAdmin()) {
			role = this.roleRepo.findById(AppConstants.ADMIN_USER).get();
		} else {
			role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
			
			
		}

		user.getRoles().add(role);

		User newUser = this.userRepo.save(user);

		return this.modelMapper.map(newUser, UserDto.class);
	}

//	public User dtoToUser(UserDto userDto) {
//		User user = new User();
//
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
//
//		return user;
//	}
//
//	public UserDto userToDto(User user) {

//		UserDto userDto = new UserDto();
//
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getPassword());
//
//		return userDto;
//	}

}
