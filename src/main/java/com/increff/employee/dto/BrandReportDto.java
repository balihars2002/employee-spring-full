package com.increff.employee.dto;

import com.increff.employee.model.BrandData;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.BrandApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BrandReportDto {
    @Autowired
    private BrandApi brandApi;
    @Autowired
    private BrandDto brandDto;

    @Transactional
    public List<BrandData> getAllList() {
        List<BrandData> list = brandDto.getAllList();
        return list;
    }
}
