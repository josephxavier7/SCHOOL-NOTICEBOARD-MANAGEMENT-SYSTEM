package com.examly.springapp.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    private String noticeTitle;

    @Column(columnDefinition = "LONGTEXT")
    private String noticeContent;

    private LocalDate noticeDate;
    private String noticeCategory;

    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.NORMAL;

    @Enumerated(EnumType.STRING)
    private Status status = Status.DRAFT;

    @Enumerated(EnumType.STRING)
    private AudienceType audienceType = AudienceType.PUBLIC;

    private LocalDateTime publishDate;
    private LocalDateTime expiryDate;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int readCount = 0;
    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public enum Priority { LOW, NORMAL, HIGH, URGENT }
    public enum Status { DRAFT, PENDING_APPROVAL, APPROVED, PUBLISHED, ARCHIVED, REJECTED }
    public enum AudienceType { PUBLIC, STUDENTS, PARENTS, TEACHERS, STAFF, CUSTOM }

    @PrePersist
    protected void onCreate() { createdDate = LocalDateTime.now(); modifiedDate = LocalDateTime.now(); }
    @PreUpdate
    protected void onUpdate() { modifiedDate = LocalDateTime.now(); }

    public Long getNoticeId() { return noticeId; }
    public void setNoticeId(Long noticeId) { this.noticeId = noticeId; }
    public String getNoticeTitle() { return noticeTitle; }
    public void setNoticeTitle(String noticeTitle) { this.noticeTitle = noticeTitle; }
    public String getNoticeContent() { return noticeContent; }
    public void setNoticeContent(String noticeContent) { this.noticeContent = noticeContent; }
    public LocalDate getNoticeDate() { return noticeDate; }
    public void setNoticeDate(LocalDate noticeDate) { this.noticeDate = noticeDate; }
    public String getNoticeCategory() { return noticeCategory; }
    public void setNoticeCategory(String noticeCategory) { this.noticeCategory = noticeCategory; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public AudienceType getAudienceType() { return audienceType; }
    public void setAudienceType(AudienceType audienceType) { this.audienceType = audienceType; }
    public LocalDateTime getPublishDate() { return publishDate; }
    public void setPublishDate(LocalDateTime publishDate) { this.publishDate = publishDate; }
    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getModifiedDate() { return modifiedDate; }
    public int getReadCount() { return readCount; }
    public void setReadCount(int readCount) { this.readCount = readCount; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
