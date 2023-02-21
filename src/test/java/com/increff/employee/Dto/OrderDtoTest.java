package com.increff.employee.Dto;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.dto.OrderDto;
import com.increff.employee.dto.OrderItemDto;
import com.increff.employee.model.data.OrderData;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryApi;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderDtoTest extends AbstractUnitTest {

    @Autowired
    private OrderDto orderDto;
    @Autowired
    private OrderItemDto orderItemDto;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private InventoryDao inventoryDao;


    @Before
    public void beforeTest(){

    }
    @Test
    public void addTest() throws ApiException {
        OrderItemForm orderItemForm1 = createOrderItemForm("barcode1",10,119.0);
        OrderItemForm orderItemForm2 = createOrderItemForm("barcode2",20,219.0);
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        orderItemFormList.add(orderItemForm1);
        orderItemFormList.add(orderItemForm2);
        orderDto.add(orderItemFormList);
        List<OrderData> orderDataList = orderDto.viewAlLOrder();
        assertEquals(1,orderDataList.size());
    }

    public OrderItemForm createOrderItemForm(String barcode,Integer quantity,Double mrp){
        OrderItemForm orderItemForm = new OrderItemForm();
        orderItemForm.setBarcode(barcode);
        orderItemForm.setMrp(mrp);
        orderItemForm.setQuantity(quantity);
        return orderItemForm;
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
