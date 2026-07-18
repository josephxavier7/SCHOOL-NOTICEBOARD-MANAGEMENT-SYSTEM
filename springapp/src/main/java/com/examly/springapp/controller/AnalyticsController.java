package com.examly.springapp.controller;

import com.examly.springapp.service.NoticeService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    private final NoticeService noticeService;

    public AnalyticsController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboard() {
        return Map.of(
            "totalNotices", noticeService.countTotal(),
            "publishedNotices", noticeService.countPublished(),
            "pendingApproval", noticeService.countPending()
        );
    }
}
