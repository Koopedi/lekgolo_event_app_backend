package com.lekgolo.eventsapi.controller;

import com.lekgolo.eventsapi.model.Comment;
import com.lekgolo.eventsapi.repository.CommentRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentRepository repository;

    public CommentController(CommentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Comment> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getById(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/post/{postId}")
    public List<Comment> getByPost(@PathVariable String postId) {
        return repository.findByPostIdOrderByCreatedAtAsc(postId);
    }

    @GetMapping("/{id}/replies")
    public List<Comment> getReplies(@PathVariable String id) {
        return repository.findByParentCommentIdOrderByCreatedAtAsc(id);
    }

    @PostMapping
    public ResponseEntity<Comment> create(@Valid @RequestBody Comment comment) {
        comment.setId(null); // always generate a fresh UUID
        Comment saved = repository.save(comment);
        // re-read so created_at (set by the database) is populated
        Comment fresh = repository.findById(saved.getId()).orElse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(fresh);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> update(@PathVariable String id, @Valid @RequestBody Comment incoming) {
        return repository.findById(id).map(existing -> {
            existing.setBody(incoming.getBody());
            return ResponseEntity.ok(repository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
