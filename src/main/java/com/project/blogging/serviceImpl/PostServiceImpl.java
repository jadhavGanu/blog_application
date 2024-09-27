package com.project.blogging.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.blogging.entities.Category;
import com.project.blogging.entities.Post;
import com.project.blogging.entities.User;
import com.project.blogging.exception.ResourceNotFoundException;
import com.project.blogging.payload.PostDto;
import com.project.blogging.payload.PostResponse;
import com.project.blogging.repositories.CategoryRepo;
import com.project.blogging.repositories.PostRepo;
import com.project.blogging.repositories.UserRepo;
import com.project.blogging.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default image");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post savedPost = this.postRepo.save(post);

		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));

		List<Post> posts = this.postRepo.findByUser(user);

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

		List<Post> posts = this.postRepo.findByCategory(category);

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return postDtos;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortedBy, String sortedByDir) {
		
		Sort sort = (sortedByDir.equalsIgnoreCase("asc"))?Sort.by(sortedBy).ascending():Sort.by(sortedBy).descending();

//		Sort sort = null;
//		if (sortedByDir.equalsIgnoreCase("asc")) {
//			sort = Sort.by(sortedBy).ascending();
//		} else {
//			sort = Sort.by(sortedBy).descending();
//		}

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> pageOfPost = this.postRepo.findAll(pageable);

		List<Post> posts = pageOfPost.getContent();

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setRecords(postDtos);
		postResponse.setPageNumber(pageOfPost.getNumber());
		postResponse.setPageSize(pageOfPost.getSize());
		postResponse.setTotalElements(pageOfPost.getTotalElements());
		postResponse.setTotalPages(pageOfPost.getTotalPages());
		postResponse.setLastPage(pageOfPost.isLast());

		return postResponse;
	}

	@Override
	public void deletePost(Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));

		this.postRepo.delete(post);

	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());

		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public List<PostDto> searchPostsByKeyword(String keyword) {
		List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

}
