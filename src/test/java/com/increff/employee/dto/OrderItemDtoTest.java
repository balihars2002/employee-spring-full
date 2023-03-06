package com.increff.employee.dto;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.pojo.*;
import com.increff.employee.api.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderItemDtoTest extends AbstractUnitTest {

    @Autowired
    private OrderItemDto orderItemDto;
    @Autowired
    private OrderDto orderDto;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private InventoryDto inventoryDto;


    private final LocalDate addDate = LocalDate.now().minusDays(1);


    @Before
    public void beforeTest(){

    }

    @Test
    public void addTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,20);
        List<InventoryPojo> inventoryPojoList = inventoryDao.getAll();
        Integer orderId = addOrder();
        OrderItemForm orderItemForm = createOrderItemForm("barcode",1,10.0);
        orderItemDto.add(orderItemForm,orderId);
    }

    @Test
    public void deleteByProductIdTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        Integer orderId = addOrder();
        OrderItemForm orderItemForm = createOrderItemForm("barcode",1,10.0);
        orderItemDto.add(orderItemForm,orderId);
        List<OrderItemData> orderItemDataList = orderItemDto.viewAlLOrderItems();
        orderItemDto.deleteByProductId(orderItemDataList.get(0).getProductId());
        orderItemDataList = orderItemDto.viewAlLOrderItems();
        assertEquals(0,orderItemDataList.size());
    }

    @Test
    public void deleteByOrderIdTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        Integer orderId = addOrder();
        OrderItemForm orderItemForm = createOrderItemForm("barcode",1,10.0);
        orderItemDto.add(orderItemForm,orderId);
        List<OrderItemData> orderItemDataList = orderItemDto.viewAlLOrderItems();
        orderItemDto.deleteByOrderId(orderItemDataList.get(0).getOrderId());
        orderItemDataList = orderItemDto.viewAlLOrderItems();
        assertEquals(1,orderItemDataList.size());
    }


    @Test(expected = ApiException.class)
    public void deleteByIdTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,2);
        Integer orderId = addOrder();
        OrderItemForm orderItemForm = createOrderItemForm("barcode",1,10.0);
        orderItemDto.add(orderItemForm,orderId);

        List<OrderItemData> orderItemDataList = orderItemDto.viewAlLOrderItems();
        orderItemDto.deleteById(orderItemDataList.get(0).getId());

    }


//    @Test
//    public void viewAllOrderItems() throws ApiException{
//        Integer brandId = addBrand("brand","category",false);
//        Integer productId = addProduct("barcode","name",10.0, brandId);
//        addInventory(productId,2);
//        Integer orderId = addOrder();
//        OrderItemForm orderItemForm = createOrderItemForm("barcode",3,10.0);
//        orderItemDto.add(orderItemForm,orderId);
//        List<OrderItemData> orderItemDataList = orderItemDto.viewAlLOrderItemsWithGivenOrderId(orderId);
//        assertEquals();
//
//    }

    @Test
    public void getDataById() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,20);
        Integer orderId = addOrder();
        OrderItemForm orderItemForm = createOrderItemForm("barcode",1,10.0);
        orderItemDto.add(orderItemForm,orderId);
        List<OrderItemData> orderItemDataList = orderItemDto.viewAlLOrderItems();
        OrderItemData orderItemData = orderItemDto.getDataById(orderItemDataList.get(0).getId());
        assertEquals("barcode",orderItemData.getBarcode());
        assertEquals("name",orderItemData.getProductName());
        assertEquals((Integer) 1,orderItemData.getQuantity());
        assertEquals( orderId , orderItemData.getOrderId());

    }

    @Test
    public void updateTest() throws ApiException{
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,20);
        Integer orderId = addOrder();
        OrderItemForm orderItemForm = createOrderItemForm("barcode",1,10.0);
        orderItemDto.add(orderItemForm,orderId);
        List<OrderItemData> orderItemDataList = orderItemDto.viewAlLOrderItems();
        OrderItemForm orderItemForm1 = createOrderItemForm("barcode",2,10.0);
        orderItemDto.update(orderItemDataList.get(0).getId(),orderItemForm1);
        orderItemDataList = orderItemDto.viewAlLOrderItems();
        assertEquals((Integer)2, orderItemDataList.get(0).getQuantity());
    }

    @Test
    public void viewAlLOrderItemsWithGivenOrderIdTest() throws ApiException{
        Integer brandId = addBrand("brand","category",false);
        Integer productId = addProduct("barcode","name",10.0, brandId);
        addInventory(productId,20);
        Integer orderId = addOrder();
        OrderItemForm orderItemForm = createOrderItemForm("barcode",1,10.0);
        orderItemDto.add(orderItemForm,orderId);
        List<OrderItemData> orderItemDataList = orderItemDto.viewAlLOrderItemsWithGivenOrderId(orderId);
        assertEquals(1,orderItemDataList.size());
    }

    public OrderItemForm createOrderItemForm(String barcode,Integer quantity,Double mrp){
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
    public Integer addBrand(String brand,String category,Boolean isDisabled){
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brand);
        brandPojo.setCategory(category);
        brandDao.insert(brandPojo);
        List<BrandPojo> brandPojoList = brandDao.getAll();
        return brandPojoList.get(0).getId();
    }

}
