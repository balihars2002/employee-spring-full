package com.increff.employee.dto;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.form.InventoryForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.api.ApiException;
import com.increff.employee.api.ProductApi;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class InventoryDtoTest extends AbstractUnitTest {

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
    public void addTest(){
        InventoryForm inventoryForm = new InventoryForm();
    }

    @Test
    public void deleteInventoryTest() throws ApiException{
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory("barcode",20);
        List<InventoryData> inventoryDataList = inventoryDto.getAllDto();
        inventoryDto.deleteInventoryById(inventoryDataList.get(0).getId());
        List<InventoryData> inventoryDataList1 = inventoryDto.getAllDto();
        assertEquals(0,inventoryDataList1.size());
    }

    @Test
    public void getAllTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory("barcode",20);
        List<InventoryData> inventoryDataList1 = inventoryDto.getAllDto();
        assertEquals(1,inventoryDataList1.size());
    }

    @Test
    public void updateTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory("barcode",20);
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("barcode");
        inventoryForm.setQuantity(25);
        List<InventoryData> inventoryDataList1 = inventoryDto.getAllDto();
        inventoryDto.updateInv(inventoryDataList1.get(0).getId(),inventoryForm);
        List<InventoryData> inventoryDataList = inventoryDto.getAllDto();
        assertEquals("barcode",inventoryDataList.get(0).getBarcode());
        assertEquals((Integer) 25,inventoryDataList.get(0).getQuantity());
    }

    @Test(expected = ApiException.class)
    public void updateTest1() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory("barcode",20);
        List<InventoryData> inventoryDataList1 = inventoryDto.getAllDto();
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("barcode");
        inventoryForm.setQuantity(25);
        inventoryDto.updateInv(inventoryDataList1.get(0).getId()+1,inventoryForm);
    }

    @Test
    public void increaseOrDecreaseInventoryTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory("barcode",20);
        List<InventoryData> inventoryDataList1 = inventoryDto.getAllDto();
        inventoryDto.increaseOrDecreaseInventory(inventoryDataList1.get(0).getId(),10,true);
        List<InventoryData> inventoryDataList = inventoryDto.getAllDto();
        assertEquals("barcode",inventoryDataList.get(0).getBarcode());
        assertEquals((Integer) 30,inventoryDataList.get(0).getQuantity());
    }

    @Test
    public void increaseOrDecreaseInventoryTest1() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory("barcode",20);
        List<InventoryData> inventoryDataList1 = inventoryDto.getAllDto();
        inventoryDto.increaseOrDecreaseInventory(inventoryDataList1.get(0).getId(),10,false);
        List<InventoryData> inventoryDataList = inventoryDto.getAllDto();
        assertEquals("barcode",inventoryDataList.get(0).getBarcode());
        assertEquals((Integer) 10,inventoryDataList.get(0).getQuantity());

    }

    @Test(expected = ApiException.class)
    public void increaseOrDecreaseInventoryTest2() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory("barcode",20);
        List<InventoryData> inventoryDataList1 = inventoryDto.getAllDto();
        inventoryDto.increaseOrDecreaseInventory(inventoryDataList1.get(0).getId(),300,false);
    }

    @Test
    public void getDataFromIdTest() throws ApiException{
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory("barcode",20);
        List<InventoryData> inventoryDataList1 = inventoryDto.getAllDto();
        InventoryData inventoryData = inventoryDto.getDataFromId(inventoryDataList1.get(0).getId());
        assertEquals("barcode",inventoryData.getBarcode());
        assertEquals((Integer) 20,inventoryData.getQuantity());
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
