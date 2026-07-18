package com.examly.springapp.repository;

import com.examly.springapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByNoticeNoticeIdAndIsActiveTrue(Long noticeId);
}
