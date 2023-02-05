package com.increff.employee.dto;

import com.increff.employee.model.data.SalesReportData;
import com.increff.employee.model.form.SalesReportForm;
import com.increff.employee.service.ApiException;
import com.increff.employee.spring.SecurityConfig;
import com.increff.employee.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class SalesReportDto {
    @Autowired
    private OrderDto orderDto;

    private static Logger logger = Logger.getLogger(SecurityConfig.class);

    public List<SalesReportData> get(SalesReportForm form) throws ApiException {
        logger.info("Entered DTO");
//       if (form.getBrand() == null && form.getCategory() == null){

        // Handle Null Dates
        if(form.getStartingDate() == null || StringUtil.isEmpty(form.getStartingDate())) {
            form.setStartingDate("1750-01-01");
        }

        if(form.getEndingDate() == null || StringUtil.isEmpty(form.getEndingDate())) {
            form.setEndingDate(LocalDate.now().toString());
        }

        // Parse String to LocalDate
        LocalDate startDateTime = LocalDate.parse(form.getStartingDate());
        LocalDate endDateTime = LocalDate.parse(form.getEndingDate());

        return orderDto.getReport(startDateTime, endDateTime, form.getBrand(), form.getCategory());
//
    }
}
