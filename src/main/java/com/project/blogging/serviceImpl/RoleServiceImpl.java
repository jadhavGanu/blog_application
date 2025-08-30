package com.project.blogging.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.project.blogging.entities.Role;
import com.project.blogging.payload.RoleDto;
import com.project.blogging.repositories.RoleRepo;
import com.project.blogging.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	private RoleRepo roleRepo;
	
	private ModelMapper modelMapper;

	public RoleServiceImpl(RoleRepo roleRepo, ModelMapper modelMapper) {
		this.roleRepo = roleRepo;
		this.modelMapper=modelMapper;
	}

	@Override
	public RoleDto createRole(RoleDto roleDto) {

		Role role = this.modelMapper.map(roleDto, Role.class);

		Role saveRole = this.roleRepo.save(role);

		return this.modelMapper.map(saveRole, RoleDto.class);
	}
	
	
	

}
