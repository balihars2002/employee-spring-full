package com.increff.employee.api;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderApiTest extends AbstractUnitTest {


    @Autowired
    private OrderApi orderApi;
    @Autowired
    private OrderDao orderDao;
    private final LocalDate addDate = LocalDate.now().minusDays(1);


    @Test
    public void addTest() {
        OrderPojo orderPojo = new OrderPojo();
//        orderPojo.setOrderTime(addZoneTime);
        orderPojo.setOrderLocalTime(addDate);
//        orderPojo.setOrderUpdateDateTime(updateZoneTime);
        orderDao.insert(orderPojo);
        List<OrderPojo> orderPojoList = orderDao.getAll();
        assertEquals(1, orderPojoList.size());
    }


    @Test
    public void updateTest() throws ApiException {
        Integer id = addOrder();
        List<OrderPojo> orderPojoList = orderDao.getAll();
        orderApi.update(orderPojoList.get(0).getId());
    }

    @Test
    public void generateInvoiceTest() throws ApiException {
        Integer id = addOrder();
        orderApi.generateInvoice(id);
        List<OrderPojo> orderPojoList = orderApi.getAll();
        assertEquals((Boolean) true, orderPojoList.get(0).getInvoiceGenerated());
    }

    @Test
    public void generateInvoiceTest1() throws ApiException {
        Integer id = addOrder();
        orderApi.generateInvoice(id + 1);
        List<OrderPojo> orderPojoList = orderApi.getAll();
        assertEquals((Boolean) false, orderPojoList.get(0).getInvoiceGenerated());
    }

    @Test
    public void selectByIdTest() {
        Integer id = addOrder();
        List<OrderPojo> orderPojoList = orderApi.getAll();
        OrderPojo orderPojo = orderApi.getById(orderPojoList.get(0).getId());
        assertEquals(id, orderPojo.getId());
        assertEquals(addDate, orderPojo.getOrderLocalTime());
//        assertEquals(addZoneTime,orderPojo.getOrderTime());
//        assertEquals(updateZoneTime,orderPojo.getOrderUpdateDateTime());
        assertEquals((Boolean) false, orderPojo.getInvoiceGenerated());
    }

    @Test
    public void selectAllTest() {
        addOrder();
        addOrder();
        List<OrderPojo> orderPojoList = orderDao.getAll();
        assertEquals(2, orderPojoList.size());
    }

    @Test
    public void selectInDateTest() {
        Integer id = addOrder();
        LocalDate startDate = LocalDate.now().minusDays(2);
        LocalDate endDate = LocalDate.now();
        List<OrderPojo> orderPojoList = orderDao.getInDate(startDate, endDate);
        assertEquals(1, orderPojoList.size());
    }

    @Test
    public void selectInDate1() {
        Integer id = addOrder();
        LocalDate startDate = LocalDate.now().minusDays(5);
        LocalDate endDate = LocalDate.now().minusDays(4);
        List<OrderPojo> orderPojoList = orderDao.getInDate(startDate, endDate);
        assertEquals(0, orderPojoList.size());
    }

    @Test
    public void getOrdersForSchedulerTest() {
        Integer id = addOrder();
        LocalDate localDate = LocalDate.now().minusDays(1);
        List<OrderPojo> orderPojoList = orderDao.getOrdersForScheduler(localDate);
        assertEquals(1, orderPojoList.size());
    }

    @Test
    public void getOrdersForSchedulerTest1() {
        Integer id = addOrder();
        LocalDate localDate = LocalDate.now();
        List<OrderPojo> orderPojoList = orderDao.getOrdersForScheduler(localDate);
        assertEquals(1, orderPojoList.size());
    }

    public Integer addOrder() {
        OrderPojo orderPojo = new OrderPojo();
//        orderPojo.setOrderTime(addZoneTime);
        orderPojo.setOrderLocalTime(addDate);
//        orderPojo.setOrderUpdateDateTime(updateZoneTime);
        orderDao.insert(orderPojo);
        List<OrderPojo> orderPojoList = orderDao.getAll();
        return orderPojoList.get(0).getId();
    }
}
