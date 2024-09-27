package com.project.blogging.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blogging.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
