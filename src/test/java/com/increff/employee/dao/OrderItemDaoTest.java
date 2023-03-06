package com.increff.employee.dao;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.pojo.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderItemDaoTest extends AbstractUnitTest {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private OrderItemDao orderItemDao;
    private final LocalDate addDate = LocalDate.now().minusDays(1);

    @Test
    public void addTest() {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand", "category", false);
        Integer productId = addProduct("barcode", "name", 10.0, brandId);
        addInventory(productId, 2);
        addOrderItem(orderId, productId, 1, 8.0);
        List<OrderItemPojo> orderItemPojoList = orderItemDao.getAll();
        assertEquals(orderId, orderItemPojoList.get(0).getOrderId());
        assertEquals(productId, orderItemPojoList.get(0).getProductId());
        assertEquals((Integer) 1, orderItemPojoList.get(0).getQuantity());
        assertEquals((Double) 8.0, orderItemPojoList.get(0).getSellingPrice());
    }

    @Test
    public void deleteByProductIdTest() {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand", "category", false);
        Integer productId = addProduct("barcode", "name", 10.0, brandId);
        addInventory(productId, 2);
        addOrderItem(orderId, productId, 1, 8.0);
        orderItemDao.deleteByProductId(productId);
        List<OrderItemPojo> orderItemPojoList = orderItemDao.getAll();
        assertEquals(0, orderItemPojoList.size());
    }

    @Test
    public void deleteByProductIdTest1() {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand", "category", false);
        Integer productId = addProduct("barcode", "name", 10.0, brandId);
        addInventory(productId, 2);
        addOrderItem(orderId, productId, 1, 8.0);
        orderItemDao.deleteByProductId(productId + 1);
        List<OrderItemPojo> orderItemPojoList = orderItemDao.getAll();
        assertEquals(1, orderItemPojoList.size());
    }

    @Test
    public void deleteByOrderIdTest() {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand", "category", false);
        Integer productId = addProduct("barcode", "name", 10.0, brandId);
        addInventory(productId, 2);
        addOrderItem(orderId, productId, 1, 8.0);
        orderItemDao.deleteByOrderId(orderId);
        List<OrderItemPojo> orderItemPojoList = orderItemDao.getAll();
        assertEquals(0, orderItemPojoList.size());
    }

    @Test
    public void deleteByOrderIdTest1() {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand", "category", false);
        Integer productId = addProduct("barcode", "name", 10.0, brandId);
        addInventory(productId, 2);
        addOrderItem(orderId, productId, 1, 8.0);
        orderItemDao.deleteByOrderId(orderId + 1);
        List<OrderItemPojo> orderItemPojoList = orderItemDao.getAll();
        assertEquals(1, orderItemPojoList.size());
    }


    @Test
    public void selectAllTest() {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand", "category", false);
        Integer productId = addProduct("barcode", "name", 10.0, brandId);
        addInventory(productId, 2);
        addOrderItem(orderId, productId, 1, 8.0);
        List<OrderItemPojo> orderItemPojoList = orderItemDao.getAll();
        assertEquals(1, orderItemPojoList.size());
    }

    @Test
    public void selectSomeTest() {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand", "category", false);
        Integer productId = addProduct("barcode", "name", 10.0, brandId);
        addInventory(productId, 2);
        addOrderItem(orderId, productId, 1, 8.0);
        List<OrderItemPojo> orderItemPojoList = orderItemDao.getByOrderId(orderId);
        assertEquals(1, orderItemPojoList.size());
    }

    @Test
    public void selectSomeTest1() {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand", "category", false);
        Integer productId = addProduct("barcode", "name", 10.0, brandId);
        addInventory(productId, 2);
        addOrderItem(orderId, productId, 1, 8.0);
        List<OrderItemPojo> orderItemPojoList = orderItemDao.getByOrderId(orderId + 1);
        assertEquals(0, orderItemPojoList.size());
    }

    @Test
    public void getPojoFromIdTest() {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand", "category", false);
        Integer productId = addProduct("barcode", "name", 10.0, brandId);
        addInventory(productId, 2);
        addOrderItem(orderId, productId, 1, 8.0);
        List<OrderItemPojo> orderItemPojoList = orderItemDao.getAll();
        OrderItemPojo orderItemPojo = orderItemDao.getById(orderItemPojoList.get(0).getId());
        assertEquals(orderId, orderItemPojoList.get(0).getOrderId());
        assertEquals(productId, orderItemPojoList.get(0).getProductId());
        assertEquals((Integer) 1, orderItemPojoList.get(0).getQuantity());
        assertEquals((Double) 8.0, orderItemPojoList.get(0).getSellingPrice());
    }

    @Test
    public void updateTest() {
        Integer orderId = addOrder();
        Integer brandId = addBrand("brand", "category", false);
        Integer productId = addProduct("barcode", "name", 10.0, brandId);
        addInventory(productId, 2);
        addOrderItem(orderId, productId, 1, 8.0);
        List<OrderItemPojo> orderItemPojoList = orderItemDao.getAll();
        orderItemDao.update(orderItemPojoList.get(0));
    }

    public void addOrderItem(Integer orderId, Integer productId, Integer quantity, Double sellingPrice) {
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setQuantity(quantity);
        orderItemPojo.setSellingPrice(sellingPrice);
        orderItemPojo.setProductId(productId);
        orderItemDao.insert(orderItemPojo);
    }

    public Integer addOrder() {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setOrderLocalTime(addDate);
        orderDao.insert(orderPojo);
        List<OrderPojo> orderPojoList = orderDao.getAll();
        return orderPojoList.get(0).getId();
    }

    public void addInventory(Integer id, Integer quantity) {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setQuantity(quantity);
        inventoryPojo.setProductId(id);
        inventoryDao.insert(inventoryPojo);
    }

    public Integer addProduct(String barcode, String name, Double mrp, Integer id) {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setBarcode(barcode);
        productPojo.setMrp(mrp);
        productPojo.setBrand_category(id);
        productDao.insert(productPojo);
        List<ProductPojo> productPojoList = productDao.getAll();
        return productPojoList.get(0).getId();
    }

    public Integer addBrand(String brand, String category, Boolean isDisabled) {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brand);
        brandPojo.setCategory(category);
        brandDao.insert(brandPojo);
        List<BrandPojo> brandPojoList = brandDao.getAll();
        return brandPojoList.get(0).getId();
    }
}
