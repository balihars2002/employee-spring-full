package com.increff.employee.dto;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.api.ApiException;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BrandReportDtoTest extends AbstractUnitTest {

    @Autowired
    private BrandReportDto brandReportDto;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private BrandDto brandDto;


    @Before
    public void beforeTest(){

    }

    @Test
    public void generateCsvTest() throws IOException, ApiException {
//        BrandForm brandForm = new BrandForm();
//        brandForm.setBrand("brand");
//        brandForm.setCategory("category");
//        brandDto.add(brandForm);
//        List<BrandData> brandDataList = brandDto.getAllList();

        MockHttpServletResponse response = new MockHttpServletResponse();
        brandReportDto.generateCsv(response);
        assertEquals("text/csv", response.getContentType());
    }

    @Test
    public void getTest() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand");
        brandForm.setCategory("category");
        brandDto.add(brandForm);
//        List<BrandData> brandDataList = brandDto.getAllList();
        List<BrandData> brandDataList1 = brandReportDto.get(brandForm);
        assertEquals(1,brandDataList1.size());
    }
    @Test
    public void getTest1() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand");
        brandForm.setCategory("category");
        brandDto.add(brandForm);

        BrandForm brandForm1 = new BrandForm();
        brandForm1.setBrand("");
        brandForm1.setCategory("category");
        List<BrandData> brandDataList1 = brandReportDto.get(brandForm1);
        assertEquals(1,brandDataList1.size());
    }
    @Test
    public void getTest2() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand");
        brandForm.setCategory("category");
        brandDto.add(brandForm);

        BrandForm brandForm1 = new BrandForm();
        brandForm1.setBrand("brand");
        brandForm1.setCategory("");
        List<BrandData> brandDataList1 = brandReportDto.get(brandForm1);
        assertEquals(1,brandDataList1.size());
    }
    @Test
    public void getTest3() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand");
        brandForm.setCategory("category");
        brandDto.add(brandForm);

        BrandForm brandForm1 = new BrandForm();
        brandForm1.setBrand("");
        brandForm1.setCategory("");
        List<BrandData> brandDataList1 = brandReportDto.get(brandForm1);
        assertEquals(1,brandDataList1.size());
    }


    public Integer addBrand(String brand,String category,Boolean isDisabled){
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brand);
        brandPojo.setCategory(category);
        brandPojo.setDisabled(isDisabled);
        brandDao.insert(brandPojo);
        List<BrandPojo> brandPojoList = brandDao.selectAll();
        return brandPojoList.get(0).getId();
    }
}
