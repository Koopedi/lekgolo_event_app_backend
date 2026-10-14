package com.lekgolo.eventsapi.repository;

import com.lekgolo.eventsapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {

    List<Comment> findByPostIdOrderByCreatedAtAsc(String postId);

    List<Comment> findByParentCommentIdOrderByCreatedAtAsc(String parentCommentId);

    List<Comment> findByAuthorId(String authorId);
}
