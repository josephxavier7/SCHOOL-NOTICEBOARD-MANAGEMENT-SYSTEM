package com.examly.springapp.repository;

import com.examly.springapp.model.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("SELECT n FROM Notice n WHERE n.isActive = true AND n.status = com.examly.springapp.model.Notice.Status.PUBLISHED ORDER BY n.priority DESC, n.publishDate DESC")
    Page<Notice> findPublishedNotices(Pageable pageable);

    @Query("SELECT n FROM Notice n WHERE n.isActive = true AND n.status = com.examly.springapp.model.Notice.Status.PUBLISHED AND n.noticeCategory = :category")
    List<Notice> findByCategory(@Param("category") String category);

    @Query("SELECT n FROM Notice n WHERE n.isActive = true AND (:keyword IS NULL OR LOWER(n.noticeTitle) LIKE LOWER(CONCAT('%',:keyword,'%')) OR LOWER(n.noticeContent) LIKE LOWER(CONCAT('%',:keyword,'%')))")
    Page<Notice> searchNotices(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT n FROM Notice n WHERE n.isActive = true AND n.status = :status")
    List<Notice> findByStatus(@Param("status") Notice.Status status);

    @Query("SELECT n FROM Notice n WHERE n.isActive = true AND n.author.id = :authorId")
    List<Notice> findByAuthorId(@Param("authorId") Long authorId);

    @Query("SELECT n FROM Notice n WHERE n.isActive = true AND n.department.id = :deptId")
    List<Notice> findByDepartmentId(@Param("deptId") Long deptId);

    @Query("SELECT COUNT(n) FROM Notice n WHERE n.status = com.examly.springapp.model.Notice.Status.PUBLISHED")
    long countPublished();

    @Query("SELECT COUNT(n) FROM Notice n WHERE n.status = com.examly.springapp.model.Notice.Status.PENDING_APPROVAL")
    long countPending();
}
