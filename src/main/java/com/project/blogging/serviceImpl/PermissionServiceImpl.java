package com.project.blogging.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.project.blogging.entities.Permission;
import com.project.blogging.payload.PermissionDto;
import com.project.blogging.repositories.PermissionRepo;
import com.project.blogging.services.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

	private PermissionRepo permissionRepo;

	private ModelMapper modelMapper;

	public PermissionServiceImpl(PermissionRepo permissionRepo, ModelMapper modelMapper) {
		this.permissionRepo = permissionRepo;

		this.modelMapper = modelMapper;
	}

	@Override
	public PermissionDto createPermission(PermissionDto permissionDto) {

		Permission permission = this.modelMapper.map(permissionDto, Permission.class);

		Permission savedPermission = this.permissionRepo.save(permission);
		PermissionDto permissionDtos = this.modelMapper.map(savedPermission, PermissionDto.class);
		return permissionDtos;
	}

}
