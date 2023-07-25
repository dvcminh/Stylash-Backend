package com.vuducminh.stylash.controller;


import com.vuducminh.stylash.controller.dto.ReportDto;
import com.vuducminh.stylash.model.Report;
import com.vuducminh.stylash.service.ReportService;
import com.vuducminh.stylash.service.UserService;
import com.vuducminh.stylash.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;


    @GetMapping("/getReportById/:id")
    public Report getReportsById(@PathVariable Long id) {
        return reportService.findById(id)
                .orElseThrow(() -> new RuntimeException("Like not found with id: " + id));
    }

}
