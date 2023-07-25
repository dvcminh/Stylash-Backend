package com.vuducminh.stylash.repository;

import com.vuducminh.stylash.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByTitleContaining(String title);
}
