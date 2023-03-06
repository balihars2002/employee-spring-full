package com.increff.employee.dto;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.api.ApiException;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.data.DailySalesData;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.pojo.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DailySalesDtoTest extends AbstractUnitTest {

    @Autowired
    private DailySalesDto dailySalesDto;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private OrderItemDto orderItemDto;

    private final LocalDate date = LocalDate.now().minusDays(1);


    @Test
    public void scheduleTest() throws ApiException {
        Integer brandId = addBrand("brand", "category", false);
        Integer productId = addProduct("barcode", "name", 10.0, brandId);
        addInventory(productId, 20);
        Integer orderId = addOrder();
        OrderItemForm orderItemForm = createOrderItemForm("barcode", 1, 10.0);
        orderItemDto.add(orderItemForm, orderId);
        dailySalesDto.scheduleTask();
        List<DailySalesData> dailySalesDataList = dailySalesDto.getAllData();
        assertEquals(1, dailySalesDataList.size());
    }


    @Test
    public void getAllTest() throws ApiException {
        Integer brandId = addBrand("brand", "category", false);
        Integer productId = addProduct("barcode", "name", 10.0, brandId);
        addInventory(productId, 20);
        Integer orderId = addOrder();
        OrderItemForm orderItemForm = createOrderItemForm("barcode", 1, 10.0);
        orderItemDto.add(orderItemForm, orderId);
        List<DailySalesData> dailySalesDataList = dailySalesDto.getAllData();
        assertEquals(0, dailySalesDataList.size());
    }

    @Test
    public void convertTest(){
        DailySalesPojo dailySalesPojo = new DailySalesPojo();
        dailySalesPojo.setLocalDate(date);
        dailySalesPojo.setTotal_revenue(100.0);
        dailySalesPojo.setInvoiced_items_count(3);
        dailySalesPojo.setInvoiced_orders_count(2);
        dailySalesPojo.setId(1);
        DailySalesData data = dailySalesDto.convertPojoToData(dailySalesPojo);
        assertEquals((Integer) 2,data.getInvoiced_orders_count());
        assertEquals((Double) 100.0, data.getTotal_revenue());
        assertEquals((Integer) 3,data.getInvoiced_items_count());
    }

    public OrderItemForm createOrderItemForm(String barcode, Integer quantity, Double mrp) {
        OrderItemForm orderItemForm = new OrderItemForm();
        orderItemForm.setBarcode(barcode);
        orderItemForm.setSellingPrice(mrp);
        orderItemForm.setQuantity(quantity);
        return orderItemForm;
    }
//    public void addOrderItem(Integer orderId,Integer productId,Integer quantity,Double sellingPrice) throws ApiException {
//        OrderItemPojo orderItemPojo = new OrderItemPojo();
//        orderItemPojo.setOrderId(orderId);
//        orderItemPojo.setQuantity(quantity);
//        orderItemPojo.setSellingPrice(sellingPrice);
//        orderItemPojo.setProductId(productId);
//        orderItemApi.add(orderItemPojo);
//    }

    public Integer addOrder() {
        OrderPojo orderPojo = new OrderPojo();
//        orderPojo.setOrderTime(addZoneTime);
        orderPojo.setOrderLocalTime(date);
//        orderPojo.setOrderUpdateDateTime(updateZoneTime);
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
