package com.examly.springapp.controller;

import com.examly.springapp.model.Notice;
import com.examly.springapp.service.NoticeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notices")
@CrossOrigin(origins = "*")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/add")
    public Notice addNotice(@RequestBody Notice notice) {
        return noticeService.addNotice(notice);
    }

    @GetMapping("/all")
    public List<Notice> getAllNotices() {
        return noticeService.getAllNotices();
    }

    @GetMapping("/{id}")
    public Optional<Notice> getNoticeById(@PathVariable Long id) {
        return noticeService.getNoticeById(id);
    }

    @PutMapping("/update/{id}")
    public Notice updateNotice(@PathVariable Long id, @RequestBody Notice notice) {
        return noticeService.updateNotice(id, notice);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
    }
}