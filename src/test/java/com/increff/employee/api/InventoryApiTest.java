package com.increff.employee.api;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class InventoryApiTest extends AbstractUnitTest {

    @Autowired
    private InventoryApi inventoryApi;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private BrandApi brandApi;
    @Autowired
    private ProductApi productApi;


    @Before
    public void beforeTest(){

    }

    @Test
    public void addTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        List<InventoryPojo> inventoryPojoList = inventoryApi.getAll();
        assertEquals(productId,inventoryPojoList.get(0).getProductId());
        assertEquals((Integer) 2,inventoryPojoList.get(0).getQuantity());
    }

    @Test
    public void selectAllTest()throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        productId = addProduct("barcode1","name1",20.0, brandId);
        addInventory(productId,3);
        assertEquals(2,inventoryDao.getAll().size());
    }



    @Test
    public void updateTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        List<InventoryPojo> inventoryPojoList = inventoryDao.getAll();
        InventoryPojo inventoryPojo = inventoryPojoList.get(0);
        inventoryApi.updateInv(inventoryPojo,5);
        List<InventoryPojo> inventoryPojoList1 = inventoryDao.getAll();
        assertEquals((Integer) 5,inventoryPojoList1.get(0).getQuantity());
    }

    @Test
    public void getPojoFromIdTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        List<InventoryPojo> inventoryPojoList = inventoryDao.getAll();
        Integer id = inventoryPojoList.get(0).getId();
        InventoryPojo inventoryPojo = inventoryApi.getById(id);
        assertEquals((Integer) 2,inventoryPojo.getQuantity());
        assertEquals( productId, inventoryPojo.getProductId());
    }

    @Test(expected = ApiException.class)
    public void getPojoFromIdTest1() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        List<InventoryPojo> inventoryPojoList = inventoryDao.getAll();
        Integer id = inventoryPojoList.get(0).getId()+1;
        InventoryPojo inventoryPojo = inventoryApi.getById(id);
    }

    @Test
    public void getPojoFromProductIdTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        List<InventoryPojo> inventoryPojoList = inventoryDao.getAll();
        Integer product_id = inventoryPojoList.get(0).getProductId();
        Integer id = inventoryPojoList.get(0).getId();
        InventoryPojo inventoryPojo = inventoryApi.getByProductId(product_id);
        assertEquals((Integer) 2,inventoryPojo.getQuantity());
        assertEquals( productId, inventoryPojo.getProductId());
        assertEquals( id,inventoryPojo.getId());
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
