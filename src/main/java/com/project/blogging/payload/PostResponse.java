package com.project.blogging.payload;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PostResponse {
	
	private List<PostDto> records;
	
	private int pageNumber;
	
	private int pageSize;
	
	private long totalElements;
	
	private  int totalPages;
	
	private boolean lastPage;

}
