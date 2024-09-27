package com.project.blogging.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blogging.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
