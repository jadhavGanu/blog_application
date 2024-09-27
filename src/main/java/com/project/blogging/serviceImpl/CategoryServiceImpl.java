package com.project.blogging.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blogging.entities.Category;
import com.project.blogging.exception.ResourceNotFoundException;
import com.project.blogging.payload.CategoryDto;
import com.project.blogging.repositories.CategoryRepo;
import com.project.blogging.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCatogory(CategoryDto categoryDto) {
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category savedCat = this.categoryRepo.save(cat);
		return this.modelMapper.map(savedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = this.categoryRepo.findAll();

		List<CategoryDto> catDtos = categories.stream()
				.map(category -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

		return catDtos;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

		category.setCategoryName(categoryDto.getCategoryName());
		category.setCategoryDescription(categoryDto.getCategoryDescription());

		Category cat = this.categoryRepo.save(category);

		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		this.categoryRepo.delete(category);
	}

}
