package com.increff.employee.dto;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.SalesReportData;
import com.increff.employee.model.SalesReportForm;
import com.increff.employee.service.ApiException;
import com.increff.employee.spring.SecurityConfig;
import com.increff.employee.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalesReportDto {
    @Autowired
    private OrderDto orderDto;

    private static Logger logger = Logger.getLogger(SecurityConfig.class);

    @Transactional
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
//       }
//       else if (form.getBrand() == null) {
//           if(form.getStartingDate() == null || StringUtil.isEmpty(form.getStartingDate())) {
//               form.setStartingDate("1750-01-01");
//           }
//
//           if(form.getEndingDate() == null || StringUtil.isEmpty(form.getEndingDate())) {
//               form.setEndingDate(LocalDate.now().toString());
//           }
//
//           // Parse String to LocalDate
//           LocalDate startDateTime = LocalDate.parse(form.getStartingDate());
//           LocalDate endDateTime = LocalDate.parse(form.getEndingDate());
//
//           return orderDto.getReportWithCategory(startDateTime, endDateTime , form.getCategory());
//       }
//       else if (form.getCategory() == null) {
//           if(form.getStartingDate() == null || StringUtil.isEmpty(form.getStartingDate())) {
//               form.setStartingDate("1750-01-01");
//           }
//
//           if(form.getEndingDate() == null || StringUtil.isEmpty(form.getEndingDate())) {
//               form.setEndingDate(LocalDate.now().toString());
//           }
//
//           // Parse String to LocalDate
//           LocalDate startDateTime = LocalDate.parse(form.getStartingDate());
//           LocalDate endDateTime = LocalDate.parse(form.getEndingDate());
//
//           return orderDto.getReport(startDateTime, endDateTime);
//       }
//       else {
//           if(form.getStartingDate() == null || StringUtil.isEmpty(form.getStartingDate())) {
//               form.setStartingDate("1750-01-01");
//           }
//
//           if(form.getEndingDate() == null || StringUtil.isEmpty(form.getEndingDate())) {
//               form.setEndingDate(LocalDate.now().toString());
//           }
//
//           // Parse String to LocalDate
//           LocalDate startDateTime = LocalDate.parse(form.getStartingDate());
//           LocalDate endDateTime = LocalDate.parse(form.getEndingDate());
//
//           return orderDto.getReport(startDateTime, endDateTime);
//       }
       // List<SalesReportData> data = new ArrayList<>();
      // return data;
    }
}
