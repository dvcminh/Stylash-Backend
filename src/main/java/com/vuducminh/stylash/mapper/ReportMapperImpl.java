package com.vuducminh.stylash.mapper;

import com.vuducminh.stylash.controller.dto.OrderDto;
import com.vuducminh.stylash.controller.dto.ReportDto;
import com.vuducminh.stylash.model.Order;
import com.vuducminh.stylash.model.Report;
import org.springframework.stereotype.Service;

@Service
public class ReportMapperImpl implements ReportMapper{
    @Override
    public ReportDto toReportDto(Report report) {
        if (report == null)
         return null;
        ReportDto.UserDto userDto = new ReportDto.UserDto(report.getUser().getUsername(),report.getUser().getFirstname(),report.getUser().getLastname(),report.getUser().getAvatar());
                return new ReportDto(report.getId(),report.getImage(),report.getTitle(),report.getDescription(),userDto);
    }
}
