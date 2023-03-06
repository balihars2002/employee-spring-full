package com.increff.employee.dto;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.api.ApiException;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.data.SalesReportData;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.model.form.SalesReportForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SalesReportDtoTest extends AbstractUnitTest {

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
    @Autowired
    private SalesReportDto salesReportDto;
    private final LocalDate addDate = LocalDate.now().minusDays(1);


    @Test
    public void generateCsvTest() throws IOException, ApiException {
        MockHttpServletResponse response = new MockHttpServletResponse();
        salesReportDto.generateCsv(response);
        assertEquals("text/csv", response.getContentType());
    }

    @Test
    public void getTest() throws ApiException {
        Integer brandId = addBrand("brand","category",false);
        Integer productId1 = addProduct("barcode1","name1",10.0, brandId);
        addInventory(productId1,12);
        OrderItemForm orderItemForm1 = createOrderItemForm("barcode1",3,9.0);
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        orderItemFormList.add(orderItemForm1);
        orderDto.add(orderItemFormList);
        SalesReportForm salesReportForm = new SalesReportForm();
        List<SalesReportData> salesReportDataList = salesReportDto.get(salesReportForm);
        assertEquals(1,salesReportDataList.size());
    }

    public OrderItemForm createOrderItemForm(String barcode,Integer quantity,Double mrp){
        OrderItemForm orderItemForm = new OrderItemForm();
        orderItemForm.setBarcode(barcode);
        orderItemForm.setQuantity(quantity);
        orderItemForm.setSellingPrice(mrp);
        return orderItemForm;
    }
    public Integer addOrder(){
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setOrderLocalTime(addDate);
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
        return productPojoList.get(productPojoList.size()-1).getId();
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
