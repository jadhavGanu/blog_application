package com.project.blogging.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.blogging.payload.CategoryDto;

public interface CategoryService {
	// create category
	CategoryDto createCatogory(CategoryDto categoryDto);

	// get
	CategoryDto getCategory(Integer categoryId);

	// get all
	List<CategoryDto> getAllCategory();

	// update

	CategoryDto updateCategory(CategoryDto categoryDto, Integer CategoryId);

	// delete
	void deleteCategory(Integer categoryId);
	
//	// get category 
//	CategoryDto getCategoryById(Integer categoryId);
	

}
