package com.project.blogging.services;

import java.util.List;

import com.project.blogging.payload.PostDto;
import com.project.blogging.payload.PostResponse;

public interface PostService {
	
	// create post
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//get posts by user
	List<PostDto> getPostsByUser(Integer userId);
	
	//get posts by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	// get post by postId
	
	PostDto getPostById(Integer postId);
	
	// get all posts 
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortedBy, String sortedByDir);
	
	// delete post
	void deletePost(Integer postId);
	
	// update post
	PostDto updatePost(PostDto postDto, Integer postId);
	
	// search post by keyword
	List<PostDto> searchPostsByKeyword(String keyword);

}
