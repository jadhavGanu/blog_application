package com.project.blogging.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blogging.entities.Comment;
import com.project.blogging.entities.Post;
import com.project.blogging.entities.User;
import com.project.blogging.exception.ResourceNotFoundException;
import com.project.blogging.payload.CommentDto;
import com.project.blogging.repositories.CommentRepo;
import com.project.blogging.repositories.PostRepo;
import com.project.blogging.repositories.UserRepo;
import com.project.blogging.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));

		Comment comment = this.modelMapper.map(commentDto, Comment.class);

		comment.setUser(user);
		comment.setPost(post);

		Comment savedComment = this.commentRepo.save(comment);

		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer userId, Integer postId, Integer commentId) {

		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "userId", userId));
		
		Post  post=this.postRepo.findById(postId).orElseThrow(()->new  ResourceNotFoundException("post", "postId", postId));
		
		Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "commentId", commentId));
		
		this.commentRepo.delete(comment);
	}

	@Override
	public CommentDto getCommentById(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("comment", "commentId", commentId));
		return this.modelMapper.map(comment, CommentDto.class);
	}

	@Override
	public List<CommentDto> getAllComment() {
		List<Comment> listOfComments = this.commentRepo.findAll();

		List<CommentDto> commentsDtos = listOfComments.stream()
				.map((comment) -> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
		return commentsDtos;
	}

	@Override
	public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
		
		 Comment comment=this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment", "commentId", commentId));
		 
		 comment.setContent(commentDto.getContent());
		 
		 Comment updatedComment=this.commentRepo.save(comment);
		 
		return this.modelMapper.map(updatedComment, CommentDto.class);
	}

}
