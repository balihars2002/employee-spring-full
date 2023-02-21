package com.increff.employee.Dto;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.model.data.BrandData;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.InventoryApi;
import com.increff.employee.service.ProductApi;
import com.increff.employee.dto.BrandDto;
import com.increff.employee.dto.ProductDto;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.model.data.ProductData;
import com.increff.employee.model.form.ProductForm;
import com.increff.employee.service.ApiException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductDtoTest extends AbstractUnitTest {
    @Autowired
    private ProductDto productDto;
    @Autowired
    private ProductApi productApi;
    @Autowired
    private BrandDto brandDto;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private InventoryApi inventoryApi;

    @Before
    public void beforeTest(){

    }
    @Test
    public void addProductTest() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand");
        brandForm.setCategory("category");
        brandDto.add(brandForm);
        ProductForm productForm = new ProductForm();
        productForm.setName("name");
        productForm.setBarcode("barcode");
        productForm.setMrp(10.5);
        productForm.setBrand("brand");
        productForm.setCategory("category");
        productDto.addDto(productForm);
        List<ProductData> list = productDto.getAllDto();
        assertEquals("brand",list.get(0).getBrand());
        assertEquals("barcode",list.get(0).getBarcode());
        assertEquals((Double) 10.5,list.get(0).getMrp());
        assertEquals("name",list.get(0).getName());
        assertEquals("category",list.get(0).getCategory());
    }

    @Test(expected = ApiException.class)
    public void addProductTest1() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand");
        brandForm.setCategory("category");
        brandDto.add(brandForm);
        ProductForm productForm = new ProductForm();
        productForm.setName("name");
        productForm.setBarcode("barcode");
        productForm.setMrp(10.5);
        productForm.setBrand("brands");
        productForm.setCategory("category");
        productDto.addDto(productForm);
    }

    @Test
    public void deleteTest() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand");
        brandForm.setCategory("category");
        brandDto.add(brandForm);
        ProductForm productForm = new ProductForm();
        productForm.setName("name");
        productForm.setBarcode("barcode");
        productForm.setMrp(10.5);
        productForm.setBrand("brands");
        productForm.setCategory("category");
        productDto.addDto(productForm);
        List<ProductPojo> productPojoList = productApi.selectAllService();
        Integer id = productPojoList.get(0).getId();
        productDto.delete(id);
        List<ProductPojo> productPojoList1 = productApi.selectAllService();
        assertEquals(0,productPojoList1.size());
    }

    @Test
    public void getCheckFromServiceTest() throws ApiException{
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand");
        brandForm.setCategory("category");
        brandDto.add(brandForm);
        ProductForm productForm = new ProductForm();
        productForm.setName("name");
        productForm.setBarcode("barcode");
        productForm.setMrp(10.5);
        productForm.setBrand("brands");
        productForm.setCategory("category");
        productDto.addDto(productForm);

    }

    @Test
    public void getAllTest() throws ApiException{
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double) 10.0,(Integer) id);
        addProduct("barcode1","name1",(Double) 20.0,(Integer) id);
        List<ProductData> productDataList = productDto.getAllDto();
        assertEquals("barcode",productDataList.get(0).getBarcode());
        assertEquals("name",productDataList.get(0).getName());
        assertEquals("brand",productDataList.get(0).getBrand());
        assertEquals("category",productDataList.get(0).getCategory());
        assertEquals((Double) 10.0 ,productDataList.get(0).getMrp());

        assertEquals("barcode1",productDataList.get(1).getBarcode());
        assertEquals("name1",productDataList.get(1).getName());
        assertEquals("brand",productDataList.get(1).getBrand());
        assertEquals("category",productDataList.get(1).getCategory());
        assertEquals((Double) 20.0 ,productDataList.get(1).getMrp());

    }

    @Test
    public void updateTest(){

    }

    @Test
    public void getQuantityFromInventoryByPIDTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        Integer quantity = productDto.getQuantityFromInventoryByPID(productId);
        assertEquals((Integer)2,quantity);
    }

    @Test(expected = ApiException.class)
    public void getQuantityFromInventoryByPIDTest1() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        Integer quantity = productDto.getQuantityFromInventoryByPID(productId+1);
    }

    @Test
    public void getDataFromBarcodeTest() throws ApiException {
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double) 10.0,(Integer) id);
        List<ProductData> productDataList = productDto.getAllDto();
        ProductData productData = productDto.getDataFromBarcode("barcode");
        assertEquals(productData,productDataList.get(0));
    }

    @Test
    public void getDataFromBarcodeTest1() throws ApiException {
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double) 10.0,(Integer) id);
        ProductData productData = productDto.getDataFromBarcode("bar");
    }

    @Test
    public void getDataFromIdTest() throws ApiException{
        Integer id = addBrand("brand","category",false);
        Integer product_id = addProduct("barcode","name",(Double) 10.0,(Integer) id);
        List<ProductData> productDataList = productDto.getAllDto();
        ProductData productData = productDto.getDataFromId(product_id);
        assertEquals(productData,productDataList.get(0));
    }

    @Test(expected = ApiException.class)
    public void getDataFromIdTest1() throws ApiException {
        Integer id = addBrand("brand", "category", false);
        Integer product_id = addProduct("barcode", "name", (Double) 10.0, (Integer) id);
        ProductData productData = productDto.getDataFromId(product_id+1);
    }


    public void addInventory(Integer id, Integer quantity){
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setQuantity(quantity);
        inventoryPojo.setProductId(id);
        inventoryApi.addService(inventoryPojo);
    }
    public Integer addProduct(String barcode,String name,Double mrp,Integer id) throws ApiException {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setBarcode(barcode);
        productPojo.setMrp(mrp);
        productPojo.setBrand_category(id);
        productApi.insertService(productPojo);
        List<ProductPojo> productPojoList = productApi.selectAllService();
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
