package com.increff.employee.dto;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.api.ProductApi;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.model.form.InventoryForm;
import com.increff.employee.model.form.ProductForm;
import com.increff.employee.api.ApiException;
import com.increff.employee.api.InventoryApi;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InventoryReportDtoTest extends AbstractUnitTest {

    @Autowired
    private InventoryReportDto inventoryReportDto;
    @Autowired
    private InventoryDto inventoryDto;
    @Autowired
    private ProductDto productDto;
    @Autowired
    private BrandDto brandDto;
    @Autowired
    private ProductApi productApi;
    @Autowired
    private BrandDao brandDao;

    @Test
    public void generateCsvTest() throws IOException, ApiException {
//        BrandForm brandForm = new BrandForm();
//        brandForm.setBrand("brand");
//        brandForm.setCategory("category");
//        brandDto.add(brandForm);
//        List<BrandData> brandDataList = brandDto.getAllList();

        MockHttpServletResponse response = new MockHttpServletResponse();
        inventoryReportDto.generateCsv(response);
        assertEquals("text/csv", response.getContentType());
    }

    @Test
    public void getTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory("barcode",20);
        List<InventoryData> inventoryDataList1 = inventoryDto.getAllDto();
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand");
        brandForm.setCategory("category");
        List<InventoryData> inventoryDataList = inventoryReportDto.get(brandForm);
        assertEquals(1,inventoryDataList.size());
    }

    @Test
    public void getTest1() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory("barcode",20);
        List<InventoryData> inventoryDataList1 = inventoryDto.getAllDto();
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("");
        brandForm.setCategory("category");
        List<InventoryData> inventoryDataList = inventoryReportDto.get(brandForm);
        assertEquals(1,inventoryDataList.size());
    }
    @Test
    public void getTest2() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory("barcode",20);
        List<InventoryData> inventoryDataList1 = inventoryDto.getAllDto();
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand");
        brandForm.setCategory("");
        List<InventoryData> inventoryDataList = inventoryReportDto.get(brandForm);
        assertEquals(1,inventoryDataList.size());
    }
    @Test
    public void getTest3() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory("barcode",20);
        List<InventoryData> inventoryDataList1 = inventoryDto.getAllDto();
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("");
        brandForm.setCategory("");
        List<InventoryData> inventoryDataList = inventoryReportDto.get(brandForm);
        assertEquals(1,inventoryDataList.size());
    }


    public void addInventory(String barcode, Integer quantity) throws ApiException {
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode(barcode);
        inventoryForm.setQuantity(quantity);
        inventoryDto.addDto(inventoryForm);
    }
    public Integer addProduct(String barcode,String name,Double mrp,Integer id) throws ApiException {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setBarcode(barcode);
        productPojo.setMrp(mrp);
        productPojo.setBrand_category(id);
        productApi.insert(productPojo);
        List<ProductPojo> productPojoList = productApi.getAll();
        return productPojoList.get(0).getId();
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
