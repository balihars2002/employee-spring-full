package com.increff.employee.dto;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.data.OrderData;
import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.form.InvoiceForm;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.api.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class OrderDtoTest extends AbstractUnitTest {

    @Autowired
    private OrderDto orderDto;
    @Autowired
    private OrderItemDto orderItemDto;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private InventoryDto inventoryDto;
//    private final ZonedDateTime addZoneTime = ZonedDateTime.now().minusDays(1);
//    private final ZonedDateTime updateZoneTime = ZonedDateTime.now();
    private final LocalDate addDate = LocalDate.now().minusDays(1);



    @Before
    public void beforeTest(){

    }
    @Test
    public void addTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId1 = addProduct("barcode1","name1",10.0, brandId);
        Integer productId2 = addProduct("barcode2","name2",20.0, brandId);
        addInventory(productId1,12);
        addInventory(productId2,52);
        OrderItemForm orderItemForm1 = createOrderItemForm("barcode1",3,1.0);
        OrderItemForm orderItemForm2 = createOrderItemForm("barcode2",4,2.0);
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        orderItemFormList.add(orderItemForm1);
        orderItemFormList.add(orderItemForm2);
        orderDto.add(orderItemFormList);
        List<OrderData> orderDataList = orderDto.viewAlLOrder();
        assertEquals(1,orderDataList.size());
    }

    @Test(expected = ApiException.class)
    public void addTest1() throws ApiException {
//        Integer orderId = addOrder();
        Integer brandId = addBrand("brand","category",false);
        Integer productId1 = addProduct("barcode1","name1",10.0, brandId);
        Integer productId2 = addProduct("barcode2","name2",20.0, brandId);
        addInventory(productId1,12);
        addInventory(productId2,52);
        OrderItemForm orderItemForm1 = createOrderItemForm("barcode1",3,119.0);
        OrderItemForm orderItemForm2 = createOrderItemForm("barcode2",100,2.0);
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        orderItemFormList.add(orderItemForm1);
        orderItemFormList.add(orderItemForm2);
        orderDto.add(orderItemFormList);
    }

    @Test
    public void deleteTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId1 = addProduct("barcode1","name1",10.0, brandId);
        addInventory(productId1,12);
        OrderItemForm orderItemForm1 = createOrderItemForm("barcode1",3,1.0);
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        orderItemFormList.add(orderItemForm1);
        orderDto.add(orderItemFormList);
        List<OrderData> orderDataList = orderDto.viewAlLOrder();
        Integer id = orderDataList.get(0).getId();
        orderDto.delete(id);
        List<OrderData> orderDataList1 = orderDto.viewAlLOrder();
        assertEquals(0,orderDataList1.size());
    }

    @Test
    public void generateInvoiceTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId1 = addProduct("barcode1","name1",10.0, brandId);
        addInventory(productId1,12);
        OrderItemForm orderItemForm1 = createOrderItemForm("barcode1",3,1.0);
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        orderItemFormList.add(orderItemForm1);
        orderDto.add(orderItemFormList);
        List<OrderData> orderDataList = orderDto.viewAlLOrder();
        Integer id = orderDataList.get(0).getId();
        orderDto.generateInvoice(id);
        List<OrderData> orderDataList1 = orderDto.viewAlLOrder();
        assertEquals(true,orderDataList1.get(0).getInvoiceGenerated());
    }

    @Test
    public void viewOrderItemsInOrderTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId1 = addProduct("barcode1","name1",10.0, brandId);
        addInventory(productId1,12);
        OrderItemForm orderItemForm1 = createOrderItemForm("barcode1",3,1.0);
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        orderItemFormList.add(orderItemForm1);
        orderDto.add(orderItemFormList);
        List<OrderData> orderDataList = orderDto.viewAlLOrder();
        List<OrderItemData> orderItemDataList = orderDto.viewOrderItemsInOrder(orderDataList.get(0).getId());
        assertEquals(1,orderItemDataList.size());
    }

    @Test
    public void viewOrderItemsInOrderTest1() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId1 = addProduct("barcode1","name1",10.0, brandId);
        addInventory(productId1,12);
        OrderItemForm orderItemForm1 = createOrderItemForm("barcode1",3,1.0);
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        orderItemFormList.add(orderItemForm1);
        orderDto.add(orderItemFormList);
        List<OrderData> orderDataList = orderDto.viewAlLOrder();
        List<OrderItemData> orderItemDataList = orderDto.viewOrderItemsInOrder(orderDataList.get(0).getId()+1);
        assertEquals(0,orderItemDataList.size());
    }

    @Test
    public void generateInvoiceForOrderTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId1 = addProduct("barcode1","name1",10.0, brandId);
        addInventory(productId1,12);
        OrderItemForm orderItemForm1 = createOrderItemForm("barcode1",3,1.0);
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        orderItemFormList.add(orderItemForm1);
        orderDto.add(orderItemFormList);
        List<OrderData> orderDataList = orderDto.viewAlLOrder();
        InvoiceForm invoiceForm = orderDto.generateInvoiceForOrder(orderDataList.get(0).getId());
        assertEquals(invoiceForm.getOrderId(),orderDataList.get(0).getId());

    }

    @Test
    public void getPdfTest() throws Exception{
        Integer brandId = addBrand("brand","category",false);
        Integer productId1 = addProduct("barcode1","name1",10.0, brandId);
        addInventory(productId1,12);
        OrderItemForm orderItemForm1 = createOrderItemForm("barcode1",3,1.0);
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        orderItemFormList.add(orderItemForm1);
        orderDto.add(orderItemFormList);
        List<OrderData> orderDataList = orderDto.viewAlLOrder();
        ResponseEntity<byte[]> response = orderDto.getPDF(orderDataList.get(0).getId());
        assertNotEquals(null,response);
    }

    public OrderItemForm createOrderItemForm(String barcode,Integer quantity,Double mrp){
        OrderItemForm orderItemForm = new OrderItemForm();
        orderItemForm.setBarcode(barcode);
        orderItemForm.setSellingPrice(mrp);
        orderItemForm.setQuantity(quantity);
        return orderItemForm;
    }
    public Integer addOrder(){
        OrderPojo orderPojo = new OrderPojo();
//        orderPojo.setOrderTime(addZoneTime);
        orderPojo.setOrderLocalTime(addDate);
//        orderPojo.setOrderUpdateDateTime(updateZoneTime);
        orderDao.insert(orderPojo);
        List<OrderPojo> orderPojoList = orderDao.selectAll();
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
        List<ProductPojo> productPojoList = productDao.selectAll();
        return productPojoList.get(productPojoList.size()-1).getId();
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
