package com.project.blogging.payload;

import java.util.HashSet;
import java.util.Set;

import com.project.blogging.entities.Permission;

import lombok.Data;

@Data
public class RoleDto {
	
	private int id;
	private String name;
	
	private Set<PermissionDto> permissions = new HashSet<>();

}
