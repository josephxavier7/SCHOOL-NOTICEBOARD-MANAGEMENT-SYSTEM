package com.examly.springapp.service;

import com.examly.springapp.model.Comment;
import com.examly.springapp.model.Notice;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.CommentRepository;
import com.examly.springapp.repository.NoticeRepository;
import com.examly.springapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired private CommentRepository commentRepository;
    @Autowired private NoticeRepository noticeRepository;
    @Autowired private UserRepository userRepository;

    public Comment addComment(Long noticeId, String username, String text) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow();
        User user = userRepository.findByUsername(username).orElseThrow();
        Comment comment = new Comment();
        comment.setNotice(notice);
        comment.setUser(user);
        comment.setCommentText(text);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByNotice(Long noticeId) {
        return commentRepository.findByNoticeNoticeIdAndIsActiveTrue(noticeId);
    }

    public Comment updateComment(Long id, String text) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        comment.setCommentText(text);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.findById(id).ifPresent(c -> {
            c.setActive(false);
            commentRepository.save(c);
        });
    }
}
