package com.project.blogging.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotEmpty
	@Size(min=3,max=10, message="category name must be min of 3 characters and max of 10 characters")
	private String categoryName;
	
	@NotEmpty
	@Size(min=10, message="category description must be min of 10 characters")
	private String categoryDescription;
	

}
