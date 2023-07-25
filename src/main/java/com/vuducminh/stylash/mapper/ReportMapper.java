package com.vuducminh.stylash.mapper;

import com.vuducminh.stylash.controller.dto.ReportDto;
import com.vuducminh.stylash.model.Report;

public interface ReportMapper {
    ReportDto toReportDto(Report report);
}
