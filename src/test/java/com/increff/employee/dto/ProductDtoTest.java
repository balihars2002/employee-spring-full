package com.increff.employee.dto;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.api.InventoryApi;
import com.increff.employee.api.ProductApi;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.model.data.ProductData;
import com.increff.employee.model.form.ProductForm;
import com.increff.employee.api.ApiException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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




    @Test
    public void addListTest() throws ApiException{
        List<ProductForm> productFormList = new ArrayList<>();
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
        productFormList.add(productForm);
        productDto.addList(productFormList);
        List<ProductData> list = productDto.getAll();
        assertEquals(1,list.size());
    }
    @Test(expected = ApiException.class)
    public void addListTest1() throws ApiException{
        List<ProductForm> productFormList = new ArrayList<>();
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
        for(int i=0;i<6000;i++) {
            productFormList.add(productForm);
        }
        productDto.addList(productFormList);

    }
    @Test
    public void addListTest2() throws ApiException{
        List<ProductForm> productFormList = new ArrayList<>();
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
        for(int i=0;i<100;i++) {
            productFormList.add(productForm);
        }
        productDto.addList(productFormList);
        List<ProductData> list = productDto.getAll();
        assertEquals(1,list.size());
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
        productDto.add(productForm);
        List<ProductData> list = productDto.getAll();
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
        productDto.add(productForm);
    }



    @Test(expected = ApiException.class)
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
        productDto.add(productForm);

    }

    @Test
    public void getAllTest() throws ApiException{
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double) 10.0,(Integer) id);
        addProduct("barcode1","name1",(Double) 20.0,(Integer) id);
        List<ProductData> productDataList = productDto.getAll();
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

    @Test(expected = ApiException.class)
    public void updateTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        ProductForm productForm = new ProductForm();
        productForm.setName("hello");
        productForm.setMrp(23.0);
        productForm.setBarcode("barcode");
        productDto.update(productId,productForm);

    }

    @Test(expected = ApiException.class)
    public void updateTest1() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        Integer productId1 = addProduct("barcodes","names",10.0, brandId);
        addInventory(productId,2);
        ProductForm productForm = new ProductForm();
        productForm.setName("names");
        productForm.setMrp(23.0);
        productForm.setBarcode("barcodes");
        productDto.update(productId,productForm);

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
        List<ProductData> productDataList = productDto.getAll();
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
        List<ProductData> productDataList = productDto.getAll();
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
        inventoryApi.add(inventoryPojo);
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
        List<BrandPojo> brandPojoList = brandDao.getAll();
        return brandPojoList.get(0).getId();
    }
}
