package com.increff.employee.service;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderApiTest extends AbstractUnitTest {


    //TODO: update test
    @Autowired
    private OrderApi orderApi;
    @Autowired
    private OrderDao orderDao;

    private final ZonedDateTime addZoneTime = ZonedDateTime.now().minusDays(1);
    private final ZonedDateTime updateZoneTime = ZonedDateTime.now();
    private final LocalDate addDate = LocalDate.now().minusDays(1);


    @Before
    public void beforeTest(){

    }

    @Test
    public void addTest(){
        Integer id = addOrder();
        List<OrderPojo> orderPojoList = orderDao.selectAll();
        OrderPojo orderPojo = orderPojoList.get(0);
        assertEquals(id,orderPojo.getId());
        assertEquals(addDate,orderPojo.getAddDate());
        assertEquals(addZoneTime,orderPojo.getOrderAddDateTime());
        assertEquals(updateZoneTime,orderPojo.getOrderUpdateDateTime());
        assertEquals((Boolean) false,orderPojo.getInvoiceGenerated());
    }

    @Test
    public void deleteTest(){
        Integer id = addOrder();
        orderApi.delete(id);
        List<OrderPojo> orderPojoList = orderDao.selectAll();
        assertEquals(0,orderPojoList.size());
    }

    @Test
    public void updateTest(){
        Integer id = addOrder();
        List<OrderPojo> orderPojoList = orderDao.selectAll();

    }

    @Test
    public void generateInvoiceTest() throws ApiException {
        Integer id = addOrder();
        orderApi.generateInvoice(id);
        List<OrderPojo> orderPojoList = orderApi.selectAll();
        assertEquals((Boolean) true,orderPojoList.get(0).getInvoiceGenerated());
    }

    @Test
    public void generateInvoiceTest1() throws ApiException {
        Integer id = addOrder();
        orderApi.generateInvoice(id+1);
        List<OrderPojo> orderPojoList = orderApi.selectAll();
        assertEquals((Boolean) false,orderPojoList.get(0).getInvoiceGenerated());
    }

    @Test
    public void selectByIdTest(){
        Integer id = addOrder();
        List<OrderPojo> orderPojoList = orderApi.selectAll();
        OrderPojo orderPojo = orderApi.selectById(orderPojoList.get(0).getId());
        assertEquals(id,orderPojo.getId());
        assertEquals(addDate,orderPojo.getAddDate());
        assertEquals(addZoneTime,orderPojo.getOrderAddDateTime());
        assertEquals(updateZoneTime,orderPojo.getOrderUpdateDateTime());
        assertEquals((Boolean) false,orderPojo.getInvoiceGenerated());
    }

    @Test
    public void selectAllTest(){
        addOrder();
        addOrder();
        List<OrderPojo> orderPojoList = orderDao.selectAll();
        assertEquals(2,orderPojoList.size());
    }

    @Test
    public void selectInDateTest(){
        Integer id = addOrder();
        LocalDate startDate = LocalDate.now().minusDays(2);
        LocalDate endDate = LocalDate.now();
        List<OrderPojo> orderPojoList = orderDao.selectInDate(startDate,endDate);
        assertEquals(1,orderPojoList.size());
    }

    @Test
    public void selectInDate1(){
        Integer id = addOrder();
        LocalDate startDate = LocalDate.now().minusDays(5);
        LocalDate endDate = LocalDate.now().minusDays(4);
        List<OrderPojo> orderPojoList = orderDao.selectInDate(startDate,endDate);
        assertEquals(0,orderPojoList.size());
    }

    @Test
    public void getOrdersForSchedulerTest(){
        Integer id = addOrder();
        LocalDate localDate = LocalDate.now().minusDays(1);
        List<OrderPojo> orderPojoList = orderDao.getOrdersForScheduler(localDate);
        assertEquals(1,orderPojoList.size());
    }

    @Test
    public void getOrdersForSchedulerTest1(){
        Integer id = addOrder();
        LocalDate localDate = LocalDate.now();
        List<OrderPojo> orderPojoList = orderDao.getOrdersForScheduler(localDate);
        assertEquals(1,orderPojoList.size());
    }

    public Integer addOrder(){
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setOrderAddDateTime(addZoneTime);
        orderPojo.setAddDate(addDate);
        orderPojo.setOrderUpdateDateTime(updateZoneTime);
        orderDao.insert(orderPojo);
        List<OrderPojo> orderPojoList = orderDao.selectAll();
        return orderPojoList.get(0).getId();
    }
}
