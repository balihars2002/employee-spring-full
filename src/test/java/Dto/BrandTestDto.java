package Dto;

import com.increff.employee.dto.BrandDto;
import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandApi;
import com.increff.employee.service.BrandTestApi;
import io.swagger.annotations.Api;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Transactional
public class BrandTestDto {
    @Autowired
    BrandDto brandDto;
    @Autowired
    BrandApi brandApi;
    @Test
    public void addBrandTest() throws ApiException{
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand");
        brandForm.setCategory("category");
        brandForm.setDisabled(false);
        brandDto.add(brandForm);
        List<BrandData> brandDataList = brandDto.getAllList();
        assertEquals("brand",brandDataList.get(0).getBrand());
        assertEquals("category",brandDataList.get(0).getCategory());
        assertFalse("false",brandDataList.get(0).getDisabled());
    }

    @Test
    public void deleteTest() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setDisabled(false);
        brandForm.setBrand("brand");
        brandForm.setCategory("category");
        brandDto.add(brandForm);
        Integer id = brandApi.getBrandCat("brand","category").getId();
        brandDto.delete(id);
        List<BrandData> dataList = brandDto.getAllList();
        assertEquals(0,dataList.size());
    }

    @Test
    public void getALLTest() throws ApiException{
        BrandForm brandForm1 = new BrandForm();
        brandForm1.setCategory("category1");
        brandForm1.setBrand("brand1");
        brandForm1.setDisabled(false);
        BrandForm brandForm2 = new BrandForm();
        brandForm2.setCategory("category2");
        brandForm2.setBrand("brand2");
        brandForm2.setDisabled(false);
        brandDto.add(brandForm1);
        brandDto.add(brandForm2);
        List<BrandData> brandDataList = brandDto.getAllList();
        assertEquals(2,brandDataList.size());

    }

    @Test
    public void updateBrandTest() throws ApiException{
        BrandForm brandForm = new BrandForm();
        brandForm.setCategory("category");
        brandForm.setBrand("brand");
        brandForm.setDisabled(false);
        brandDto.add(brandForm);
        List<BrandData> brandDataList = brandDto.getAllList();
        Integer id = brandDataList.get(0).getId();
        brandForm.setBrand("updatedBrand");
        brandDto.updateList(id,brandForm);
        assertEquals("updatedBrand",brandDataList.get(0).getBrand());
    }


//    @Test
//    public void getBrandTest() throws ApiException{
//
//    }
}
