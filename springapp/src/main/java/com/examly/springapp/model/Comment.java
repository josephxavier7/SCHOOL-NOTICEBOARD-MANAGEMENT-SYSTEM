package com.examly.springapp.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String commentText;

    @ManyToOne
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime createdDate;
    private boolean isActive = true;

    @PrePersist
    protected void onCreate() { createdDate = LocalDateTime.now(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCommentText() { return commentText; }
    public void setCommentText(String commentText) { this.commentText = commentText; }
    public Notice getNotice() { return notice; }
    public void setNotice(Notice notice) { this.notice = notice; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
