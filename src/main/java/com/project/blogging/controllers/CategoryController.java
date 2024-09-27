package com.project.blogging.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blogging.payload.ApiResponse;
import com.project.blogging.payload.CategoryDto;
import com.project.blogging.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// create category
	@PostMapping("/create")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto catDto = this.categoryService.createCatogory(categoryDto);
		return new ResponseEntity<>(catDto, HttpStatus.CREATED);
	}

	// get category
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Integer category_Id) {
		CategoryDto categoryDto = this.categoryService.getCategory(category_Id);
		return new ResponseEntity<>(categoryDto, HttpStatus.OK);
	}

	// get All categories
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCatogories() {
		List<CategoryDto> categoryDtos = this.categoryService.getAllCategory();
		return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
	}

	// update category
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable("categoryId") Integer category_Id) {
		CategoryDto catDto = this.categoryService.updateCategory(categoryDto, category_Id);
		return new ResponseEntity<>(catDto, HttpStatus.OK);
	}

	// delete category
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(new ApiResponse("category delete successfully....", true), HttpStatus.OK);
	}

}
