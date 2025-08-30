package com.project.blogging.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blogging.entities.Permission;

public interface PermissionRepo extends JpaRepository<Permission, Long> {
	
	Optional<Permission> findByPermissionName(String name);

}
