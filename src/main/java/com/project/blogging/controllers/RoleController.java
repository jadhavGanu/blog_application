package com.project.blogging.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blogging.payload.RoleDto;
import com.project.blogging.services.RoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping("/createRole")
	public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {

		RoleDto createRole = this.roleService.createRole(roleDto);

		return new ResponseEntity<RoleDto>(createRole, HttpStatus.CREATED);

	}

}
