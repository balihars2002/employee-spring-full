package com.increff.employee.dao;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class InventoryDaoTest extends AbstractUnitTest {

    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private BrandDao brandDao;

    @Before
    public void beforeTest(){

    }


    @Test
    public void insertTest(){
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        List<InventoryPojo> inventoryPojoList = inventoryDao.getAll();
        assertEquals(productId,inventoryPojoList.get(0).getProductId());
        assertEquals((Integer) 2,inventoryPojoList.get(0).getQuantity());
    }

//    @Test(expected = Exception.class)
//    public void insertTest1(){
//        Integer brandId = addBrand("brand","category",false);
//        Integer productId = addProduct("barcode","name",10.0, brandId);
//        addInventory(productId+1,2);
//    }

    @Test
    public void selectAllTest(){
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        productId = addProduct("barcode1","name1",20.0, brandId);
        addInventory(productId,3);
        assertEquals(2,inventoryDao.getAll().size());
    }


//    @Test(expected = Exception.class)
//    public void deleteTest1(){
//        Integer brandId = addBrand("brand","category",false);
//        Integer productId = addProduct("barcode","name",10.0, brandId);
//        addInventory(productId,2);
//        List<InventoryPojo> inventoryPojoList = inventoryDao.selectAlls();
//        inventoryDao.delete(inventoryPojoList.get(0).getId()+1);
//    }

    @Test(expected = Exception.class)
    public void selectPojoByBarcodeTest(){
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        List<InventoryPojo> inventoryPojoList = inventoryDao.getAll();
        String bar = "barcode";
        InventoryPojo inventoryPojo = inventoryDao.getByBarcode(bar);
//        assertEquals(productId,inventoryPojo.getProductId());
//        assertEquals((Integer) 2,inventoryPojo.getQuantity());
    }

    @Test
    public void selectPojoByIdTest(){
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        List<InventoryPojo> inventoryPojoList = inventoryDao.getAll();
        InventoryPojo inventoryPojo = inventoryDao.getById(inventoryPojoList.get(0).getId());
        assertEquals(productId,inventoryPojo.getProductId());
        assertEquals((Integer) 2,inventoryPojo.getQuantity());
    }

    @Test
    public void selectPojoByProductIdTest(){
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        List<InventoryPojo> inventoryPojoList = inventoryDao.getAll();
        InventoryPojo inventoryPojo = inventoryDao.getByProductId(inventoryPojoList.get(0).getProductId());
        assertEquals(productId,inventoryPojo.getProductId());
        assertEquals((Integer) 2,inventoryPojo.getQuantity());
    }

    @Test
    public void updateTest(){
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        List<InventoryPojo> inventoryPojoList = inventoryDao.getAll();
        InventoryPojo inventoryPojo = inventoryPojoList.get(0);
        inventoryDao.update(inventoryPojo);
    }



    public void addInventory(Integer id, Integer quantity){
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setQuantity(quantity);
        inventoryPojo.setProductId(id);
        inventoryDao.insert(inventoryPojo);
    }
    public Integer addProduct(String barcode,String name,Double mrp,Integer id){
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setBarcode(barcode);
        productPojo.setMrp(mrp);
        productPojo.setBrand_category(id);
        productDao.insert(productPojo);
        List<ProductPojo> productPojoList = productDao.getAll();
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
