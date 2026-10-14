package com.lekgolo.eventsapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Maps to the `comments` table:
 * id (char36 PK), post_id (char36), parent_comment_id (char36, nullable),
 * author_id (char36), body (varchar 2200), created_at (timestamp, default current_timestamp)
 */
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @Column(name = "id", length = 36, nullable = false, updatable = false)
    private String id;

    @NotBlank
    @Column(name = "post_id", length = 36, nullable = false)
    private String postId;

    @Column(name = "parent_comment_id", length = 36)
    private String parentCommentId;

    @NotBlank
    @Column(name = "author_id", length = 36, nullable = false)
    private String authorId;

    @NotBlank
    @Size(max = 2200)
    @Column(name = "body", length = 2200, nullable = false)
    private String body;

    // Filled by the database default (current_timestamp)
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void generateId() {
        if (id == null || id.isBlank()) {
            id = UUID.randomUUID().toString();
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }

    public String getParentCommentId() { return parentCommentId; }
    public void setParentCommentId(String parentCommentId) { this.parentCommentId = parentCommentId; }

    public String getAuthorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
