package com.project.blogging.services;

import java.util.List;

import com.project.blogging.payload.CommentDto;

public interface CommentService {
	
	//   create comment
	 CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId);
	 
	 // delete comment
	 void deleteComment(Integer userId, Integer postId, Integer commentId);
	 
	 // get comment
	 CommentDto getCommentById(Integer commentId);
	 
	 // get All comments
	 List<CommentDto> getAllComment();
	 
	 //update comment
	 CommentDto updateComment(CommentDto commentDto, Integer commentId);

}
