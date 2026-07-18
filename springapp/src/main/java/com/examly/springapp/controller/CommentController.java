package com.examly.springapp.controller;

import com.examly.springapp.model.Comment;
import com.examly.springapp.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/api/notices/{noticeId}/comments")
    public Comment add(@PathVariable Long noticeId, @RequestBody Map<String, String> body, Principal principal) {
        return commentService.addComment(noticeId, principal.getName(), body.get("commentText"));
    }

    @GetMapping("/api/notices/{noticeId}/comments")
    public List<Comment> getAll(@PathVariable Long noticeId) {
        return commentService.getCommentsByNotice(noticeId);
    }

    @PutMapping("/api/comments/{id}")
    public Comment update(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return commentService.updateComment(id, body.get("commentText"));
    }

    @DeleteMapping("/api/comments/{id}")
    public void delete(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
