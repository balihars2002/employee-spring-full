package com.increff.employee.dto;

import com.increff.employee.model.data.BrandData;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.BrandApi;
import com.increff.employee.util.CsvFileGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.dto.HelperDto.convertBrandPojoToData;

@Service
public class BrandReportDto {
    @Autowired
    private BrandApi brandApi;

    @Autowired
    private CsvFileGenerator csvFileGenerator;


    public List<BrandData> getAllList() {
        List<BrandPojo> brandPojoList = brandApi.getAll();
        List<BrandData> brandDataList = new ArrayList<>();
        for(BrandPojo pojo:brandPojoList) {
            BrandData data = convertBrandPojoToData(pojo);
            brandDataList.add(data);
        }
        return brandDataList;
    }

    public void generateCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"brandReport.csv\"");
        List<BrandPojo> brandPojoList = brandApi.getAll();
        csvFileGenerator.writeBrandsToCsv(brandPojoList, response.getWriter());
        brandPojoList.clear();
    }
}
