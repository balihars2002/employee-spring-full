package com.increff.employee.dao;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.pojo.OrderPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderDaoTest extends AbstractUnitTest {

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

//    private final ZonedDateTime addZoneTime = ZonedDateTime.now().minusDays(1);
//    private final ZonedDateTime updateZoneTime = ZonedDateTime.now();
    private final LocalDate addDate = LocalDate.now();

    @Before
    public void beforeTest(){

    }

    @Test
    public void addTest(){
        Integer id = addOrder();
        List<OrderPojo> orderPojoList = orderDao.getAll();
        OrderPojo orderPojo = orderPojoList.get(0);
        assertEquals(id,orderPojo.getId());
        assertEquals(addDate,orderPojo.getOrderLocalTime());
//        assertEquals(addZoneTime,orderPojo.getOrderTime());
//        assertEquals(updateZoneTime,orderPojo.getOrderUpdateDateTime());
        assertEquals((Boolean) false,orderPojo.getInvoiceGenerated());
    }



    @Test
    public void selectAllTest(){
        addOrder();
        addOrder();
        List<OrderPojo> orderPojoList = orderDao.getAll();
        assertEquals(2,orderPojoList.size());
    }

    @Test
    public void selectInDate(){
        Integer id = addOrder();
        LocalDate startDate = LocalDate.now().minusDays(2);
        LocalDate endDate = LocalDate.now();
        List<OrderPojo> orderPojoList = orderDao.getInDate(startDate,endDate);
        assertEquals(1,orderPojoList.size());
    }


    @Test
    public void selectByIdTest(){
        Integer id = addOrder();
        OrderPojo orderPojo = orderDao.getById(id);
        assertEquals(id,orderPojo.getId());
        assertEquals(addDate,orderPojo.getOrderLocalTime());
//        assertEquals(addZoneTime,orderPojo.getOrderTime());
//        assertEquals(updateZoneTime,orderPojo.getOrderUpdateDateTime());
        assertEquals((Boolean) false,orderPojo.getInvoiceGenerated());
    }

    @Test
    public void getOrdersForSchedulerTest(){
         Integer id = addOrder();
         LocalDate localDate = LocalDate.now().minusDays(1);
         List<OrderPojo> orderPojoList = orderDao.getOrdersForScheduler(localDate);
         assertEquals(0,orderPojoList.size());
    }

    @Test
    public void updateTest(){
        Integer id = addOrder();
        List<OrderPojo> orderPojoList = orderDao.getAll();
        OrderPojo orderPojo = orderPojoList.get(0);
        orderDao.update(orderPojo);
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

}
