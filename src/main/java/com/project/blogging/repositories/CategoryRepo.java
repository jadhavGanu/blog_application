package com.project.blogging.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blogging.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
