package com.increff.employee.dto;

import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.api.BrandApi;
import com.increff.employee.util.CsvFileGenerator;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.increff.employee.dto.HelperDto.convertBrandPojoToData;

@Service
public class BrandReportDto {
    @Autowired
    private BrandApi brandApi;

    @Autowired
    private CsvFileGenerator csvFileGenerator;

    List<BrandData> brandDataList = new ArrayList<>();
    public List<BrandData> get(BrandForm form){
        brandDataList.clear();
        form.setDisabled(false);
        List<BrandPojo> brandPojoList = brandApi.getAll();
        HashMap<BrandData,Integer> map = new HashMap<>();
        for(BrandPojo pojo:brandPojoList) {
            BrandData data = convertBrandPojoToData(pojo);
            if(!StringUtil.isEmpty(form.getBrand()) && (!StringUtil.isEmpty(form.getCategory()))){
                if(form.getBrand().equals(data.getBrand()) && form.getCategory().equals(data.getCategory())){
                    brandDataList.add(data);
                }
            }
            else if(!StringUtil.isEmpty(form.getBrand())){
                if(form.getBrand().equals(data.getBrand())){
                    brandDataList.add(data);
                }
            }
            else if(!StringUtil.isEmpty(form.getCategory())){
                if( form.getCategory().equals(data.getCategory())){
                    brandDataList.add(data);
                }
            }
            else{
                brandDataList.add(data);
            }
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
