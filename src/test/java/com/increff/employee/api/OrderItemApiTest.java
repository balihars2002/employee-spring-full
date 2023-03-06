package com.increff.employee.api;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.dao.*;
import com.increff.employee.pojo.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderItemApiTest extends AbstractUnitTest {


    @Autowired
    private OrderItemApi orderItemApi;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private ProductDao productDao;
//    private final ZonedDateTime addZoneTime = ZonedDateTime.now().minusDays(1);
//    private final ZonedDateTime updateZoneTime = ZonedDateTime.now();
    private final LocalDate addDate = LocalDate.now().minusDays(1);


    @Before
    public void beforeTest(){

    }

    @Test
    public void addTest() throws ApiException {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand","category");
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        addOrderItem(orderId,productId,1,8.0);
        List<OrderItemPojo> orderItemPojoList = orderItemApi.getAll();
        assertEquals(orderId,orderItemPojoList.get(0).getOrderId());
        assertEquals(productId,orderItemPojoList.get(0).getProductId());
        assertEquals((Integer) 1,orderItemPojoList.get(0).getQuantity());
        assertEquals((Double) 8.0 ,orderItemPojoList.get(0).getSellingPrice());
    }
    @Test(expected = ApiException.class)
    public void addTest1() throws ApiException {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand","category");
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        addOrderItem(orderId,productId,3,2.0);
    }

    @Test
    public void deleteByProductIdTest() throws ApiException {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand","category");
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,3);
        addOrderItem(orderId,productId,2,8.0);
        orderItemApi.deleteByProductId(productId);
        List<OrderItemPojo> orderItemPojoList = orderItemApi.getAll();
        assertEquals(0,orderItemPojoList.size());
    }

    @Test
    public void selectAllTest() throws ApiException {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand","category");
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,3);
        addOrderItem(orderId,productId,2,8.0);
        List<OrderItemPojo> orderItemPojoList = orderItemApi.getAll();
        assertEquals(1,orderItemPojoList.size());
    }

    @Test(expected = ApiException.class)
    public void updateTest() throws ApiException {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand","category");
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,3);
        addOrderItem(orderId,productId,2,8.0);
        List<OrderItemPojo> orderItemPojoList = orderItemApi.getAll();

        OrderItemPojo orderItemPojo = orderItemPojoList.get(0);
        orderItemApi.update(orderItemPojo.getId(),0,"barcode",1.5);
//        orderItemPojoList = orderItemApi.getAll();
//        assertEquals(1,orderItemPojoList.size());
    }
    @Test
    public void selectSomeTest() throws ApiException {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand","category");
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,3);
        addOrderItem(orderId,productId,2,8.0);
        List<OrderItemPojo> orderItemPojoList = orderItemApi.getByOrderId(orderId);
        assertEquals(1,orderItemPojoList.size());
    }

    @Test
    public void selectSomeTest1() throws ApiException {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand","category");
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,3);
        addOrderItem(orderId,productId,2,8.0);
        List<OrderItemPojo> orderItemPojoList = orderItemApi.getByOrderId(orderId+1);
        assertEquals(0,orderItemPojoList.size());
    }

    @Test
    public void getPojoFromIdTest() throws ApiException {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand","category");
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,3);
        addOrderItem(orderId,productId,2,8.0);
        OrderItemPojo orderItemPojo = orderItemApi.getById(orderId);
    }

    public void addOrderItem(Integer orderId,Integer productId,Integer quantity,Double sellingPrice) throws ApiException {
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setQuantity(quantity);
        orderItemPojo.setSellingPrice(sellingPrice);
        orderItemPojo.setProductId(productId);
        orderItemApi.add(orderItemPojo);
    }

    public Integer addOrder(){
        OrderPojo orderPojo = new OrderPojo();
//        orderPojo.setOrderTime(addZoneTime);
        orderPojo.setOrderLocalTime(addDate);
//        orderPojo.setOrderUpdateDateTime(updateZoneTime);
        orderDao.insert(orderPojo);
        List<OrderPojo> orderPojoList = orderDao.getAll();
        return orderPojoList.get(0).getId();
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
    public Integer addBrand(String brand,String category){
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brand);
        brandPojo.setCategory(category);
        brandDao.insert(brandPojo);
        List<BrandPojo> brandPojoList = brandDao.getAll();
        return brandPojoList.get(0).getId();
    }
}
