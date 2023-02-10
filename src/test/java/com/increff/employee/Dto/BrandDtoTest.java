package com.increff.employee.Dto;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.service.BrandApi;
import com.increff.employee.dto.BrandDto;
import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BrandDtoTest extends AbstractUnitTest {
    @Autowired
    BrandDto brandDto;
    @Autowired
    BrandApi brandApi;
    @Test
    public void addBrandTest() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand");
        brandForm.setCategory("category");
        brandForm.setDisabled(false);
        brandDto.add(brandForm);
        List<BrandData> brandDataList = brandDto.getAllList();
        assertEquals("brand",brandDataList.get(0).getBrand());
        assertEquals("category",brandDataList.get(0).getCategory());
       // assertFalse(String.valueOf((Boolean) false),brandDataList.get(0).getDisabled());
    }

    @Test
    public void addBrandTest1() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("");
        brandForm.setCategory("category");
        brandForm.setDisabled(false);
        Boolean temp = false;
        try {
           brandDto.add(brandForm);
        } catch (Exception e) {
            temp = true;
           // System.out.println("Something went wrong.");
        }
        assertEquals(true,temp);
    }

    @Test
    public void addBrandTest3() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand");
        brandForm.setCategory("category");
        brandForm.setDisabled(false);
        Boolean temp = false;
        brandDto.add(brandForm);
        try {
            brandDto.add(brandForm);
        } catch (Exception e) {
            temp = true;
        }
        assertEquals(true,temp);
    }

    @Test
    public void addBrandTest2() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand");
        brandForm.setCategory("");
        brandForm.setDisabled(false);
        Boolean temp = false;
        try {
            brandDto.add(brandForm);
        } catch (Exception e) {
            temp = true;
            // System.out.println("Something went wrong.");
        }
        assertEquals(true,temp);
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
    public void getALLTest1() throws ApiException{
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
        brandDto.delete(brandDataList.get(0).getId());
        List<BrandData> brandDataList1 = brandDto.getAllList();
        assertEquals(1,brandDataList1.size());
    }

    @Test
    public void  deleteTest() throws ApiException {
        BrandForm brandForm1 = new BrandForm();
        brandForm1.setCategory("category1");
        brandForm1.setBrand("brand1");
        brandForm1.setDisabled(false);
        brandDto.add(brandForm1);
        List<BrandData> brandDataList = brandDto.getAllList();
        Integer id = brandDataList.get(0).getId();
        brandDto.delete(id);
        brandDataList = brandDto.getAllList();
        assertEquals(0,brandDataList.size());
    }

    @Test
    public void normalizeBrandTest() throws ApiException {
        BrandPojo pojo = new BrandPojo();
        pojo.setCategory("caTegOrY");
        pojo.setBrand("BraND");
        brandDto.normalizeBrandPojo(pojo);
        assertEquals("brand",pojo.getBrand());
        assertEquals("category",pojo.getCategory());
    }
    @Test
    public void updateListTest() throws ApiException{
        BrandForm brandForm = new BrandForm();
        brandForm.setCategory("category");
        brandForm.setBrand("brand");
        brandForm.setDisabled(false);
        brandDto.add(brandForm);
        List<BrandData> brandDataList = brandDto.getAllList();
        Integer id = brandDataList.get(0).getId();
        BrandForm brandForm1 = new BrandForm();
        brandForm1.setBrand("updatedBrand");
        brandForm1.setCategory("category");
        brandDto.updateList(id,brandForm1);
        List<BrandData> brandDataList1 = brandDto.getAllList();
        assertEquals("updatedbrand",brandDataList1.get(0).getBrand());
    }
    @Test
    public void updateListTest1() throws ApiException{
        BrandForm brandForm = new BrandForm();
        brandForm.setCategory("category");
        brandForm.setBrand("brand");
        brandForm.setDisabled(false);
        brandDto.add(brandForm);
        List<BrandData> brandDataList = brandDto.getAllList();
        Integer id = brandDataList.get(0).getId();
        BrandForm brandForm1 = new BrandForm();
        brandForm1.setBrand("brand");
        brandForm1.setCategory("updatedcategory");
        brandDto.updateList(id,brandForm1);
        List<BrandData> brandDataList1 = brandDto.getAllList();
        assertEquals("updatedcategory",brandDataList1.get(0).getCategory());
    }

    @Test
    public void getCheckFromServiceTest() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setCategory("category");
        brandForm.setBrand("brand");
        brandForm.setDisabled(false);
        brandDto.add(brandForm);
        List<BrandData> brandDataList = brandDto.getAllList();
        Integer id = brandDataList.get(0).getId();
        BrandPojo pojo = brandDto.getCheckFromService(id);
        assertEquals("brand",pojo.getBrand());
        assertEquals("category",pojo.getCategory());
        assertEquals((Boolean) false,pojo.getDisabled());
    }

    @Test(expected = ApiException.class)
    public void getCheckFromServiceTest1() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setCategory("category");
        brandForm.setBrand("brand");
        brandForm.setDisabled(false);
        brandDto.add(brandForm);
        List<BrandData> brandDataList = brandDto.getAllList();
        Integer id = brandDataList.get(0).getId()+1;
        BrandPojo pojo = brandDto.getCheckFromService(id);
    }
    @Test(expected = ApiException.class)
    public void checkDuplicateTest() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setCategory("category");
        brandForm.setBrand("brand");
        brandForm.setDisabled(false);
        brandDto.add(brandForm);
        List<BrandData> brandDataList = brandDto.getAllList();
        String brand = brandDataList.get(0).getBrand();
        String category = brandDataList.get(0).getCategory();
        brandDto.getCheckBrand(brand,category);

    }
    @Test
    public void checkDuplicateTest1() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setCategory("category");
        brandForm.setBrand("brand");
        brandForm.setDisabled(false);
        brandDto.add(brandForm);
        List<BrandData> brandDataList = brandDto.getAllList();
        String brand = "brands";
        String category = "categories";
        Boolean temp = false;
        try{
            brandDto.getCheckBrand(brand,category);
        }
        catch (Exception e){
            temp = true;
        }
        assertEquals((Boolean) false, temp);
    }

    @Test
    public void validateBrandFormTest() throws ApiException {
        Integer count = 0;
        BrandForm brandForm = new BrandForm();
        brandForm.setCategory("category");
        brandForm.setBrand("brand");
        brandForm.setDisabled(false);
        try{
            brandDto.validateBrandForm(brandForm);
        } catch (Exception e){
            count ++;
        }
        brandForm.setCategory("");
        try{
            brandDto.validateBrandForm(brandForm);
        } catch (Exception e){
            count ++;
        }
        brandForm.setCategory("categ");
        brandForm.setBrand("");
        try{
            brandDto.validateBrandForm(brandForm);
        } catch (Exception e){
            count ++;
        }
        assertEquals((Integer) 2,count);
    }
}
