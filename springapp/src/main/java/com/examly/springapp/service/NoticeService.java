package com.examly.springapp.service;

import com.examly.springapp.model.Notice;
import com.examly.springapp.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    @Autowired private NoticeRepository noticeRepository;

    public Notice addNotice(Notice notice) {
        notice.setStatus(Notice.Status.DRAFT);
        return noticeRepository.save(notice);
    }

    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    public Optional<Notice> getNoticeById(Long id) {
        return noticeRepository.findById(id);
    }

    public Notice updateNotice(Long id, Notice notice) {
        notice.setNoticeId(id);
        return noticeRepository.save(notice);
    }

    public void deleteNotice(Long id) {
        noticeRepository.findById(id).ifPresent(n -> {
            n.setActive(false);
            noticeRepository.save(n);
        });
    }

    public Page<Notice> getPublishedNotices(int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return noticeRepository.findPublishedNotices(pageable);
    }

    public Page<Notice> searchNotices(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return noticeRepository.searchNotices(keyword, pageable);
    }

    public Notice submitForApproval(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow();
        notice.setStatus(Notice.Status.PENDING_APPROVAL);
        return noticeRepository.save(notice);
    }

    public Notice approveNotice(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow();
        notice.setStatus(Notice.Status.APPROVED);
        return noticeRepository.save(notice);
    }

    public Notice rejectNotice(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow();
        notice.setStatus(Notice.Status.REJECTED);
        return noticeRepository.save(notice);
    }

    public Notice publishNotice(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow();
        notice.setStatus(Notice.Status.PUBLISHED);
        notice.setPublishDate(LocalDateTime.now());
        return noticeRepository.save(notice);
    }

    public List<Notice> getByStatus(Notice.Status status) {
        return noticeRepository.findByStatus(status);
    }

    public List<Notice> getByCategory(String category) {
        return noticeRepository.findByCategory(category);
    }

    public List<Notice> getByDepartment(Long deptId) {
        return noticeRepository.findByDepartmentId(deptId);
    }

    public long countPublished() { return noticeRepository.countPublished(); }
    public long countPending() { return noticeRepository.countPending(); }
    public long countTotal() { return noticeRepository.count(); }
}
