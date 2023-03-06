package com.increff.employee.dto;

import com.increff.employee.api.BrandApi;
import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.pojo.BrandPojo;
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

    private List<BrandData> brandDataList = new ArrayList<>();

    public List<BrandData> get(BrandForm form) {
        brandDataList.clear();
        List<BrandPojo> brandPojoList = brandApi.getBrandReport(form.getBrand());
        for(BrandPojo brandPojo : brandPojoList){
            brandDataList.add(convertBrandPojoToData(brandPojo));
        }
        return brandDataList;
    }

    public void generateCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"brandReport.csv\"");
        csvFileGenerator.writeBrandsToCsv(brandDataList, response.getWriter());
        brandDataList.clear();
    }


}
