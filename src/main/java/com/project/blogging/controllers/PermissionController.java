package com.project.blogging.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blogging.payload.PermissionDto;
import com.project.blogging.services.PermissionService;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	
	@PostMapping("/createPermission")
	public ResponseEntity<PermissionDto> createPermission(@RequestBody PermissionDto permissionDto)
	{
		return new ResponseEntity<>(this.permissionService.createPermission(permissionDto), HttpStatus.CREATED);
	}
}
