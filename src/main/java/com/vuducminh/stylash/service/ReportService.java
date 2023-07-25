package com.vuducminh.stylash.service;

import com.vuducminh.stylash.model.Product;
import com.vuducminh.stylash.model.Report;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    List<Report> findAll();
    List<Report> findAllByTitle(String title);
    Optional<Report> findById(Long id);
    void deleteReport(Report report);
    Report save(Report report);
}
