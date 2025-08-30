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
import com.project.blogging.payload.CommentDto;
import com.project.blogging.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	
	// create comment
	@PostMapping("/user/{userId}/post/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commnetDto,
			@PathVariable("userId") Integer user_id, 
			@PathVariable("postId") Integer postId){
		
		CommentDto comment=this.commentService.createComment(commnetDto, user_id, postId);
		return new ResponseEntity<>(comment, HttpStatus.CREATED);
	}
	
	// delete comment
	@DeleteMapping("/user/{userId}/post/{postId}/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("userId") Integer user_id, 
			@PathVariable("postId") Integer postId,
			@PathVariable Integer commentId) {
		this.commentService.deleteComment(user_id, postId, commentId);
		return new ResponseEntity<>(new ApiResponse("Comment successfully deleted....", true), HttpStatus.OK);
	}
	
	// get comment
	@GetMapping("/{commentId}")
	public ResponseEntity<CommentDto> getComment(@PathVariable("commentId") Integer commentId) {
		CommentDto commentDto = this.commentService.getCommentById(commentId);
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}
	
	// get all comments
	@GetMapping("/")
	public ResponseEntity<List<CommentDto>> getAllComments() {
		List<CommentDto> listOfComments = this.commentService.getAllComment();
		return new ResponseEntity<>(listOfComments, HttpStatus.OK);
	}
	
	// updated comment
	@PutMapping("/{commentId}")
	public ResponseEntity<CommentDto> updatedComment(@RequestBody CommentDto commentDto, @PathVariable Integer commentId) {
		CommentDto comment = this.commentService.updateComment(commentDto, commentId);
		return new ResponseEntity<CommentDto>(comment, HttpStatus.OK);
	}
}
