package com.increff.employee.dao;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.pojo.DailySalesPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DailySalesDaoTest extends AbstractUnitTest {

    @Autowired
    private DailySalesDao dailySalesDao;
    private final LocalDate date = LocalDate.now().minusDays(1);


    @Test
    public void insertTest() {
        addDailySales();
        List<DailySalesPojo> dailySalesPojosList = dailySalesDao.getAll();
        assertEquals(date, dailySalesPojosList.get(0).getLocalDate());
        assertEquals((Integer) 3, dailySalesPojosList.get(0).getInvoiced_orders_count());
        assertEquals((Integer) 10, dailySalesPojosList.get(0).getInvoiced_items_count());
        assertEquals((Double) 1000.0, dailySalesPojosList.get(0).getTotal_revenue());
    }

    @Test
    public void selectAllTest() {
        addDailySales();
        List<DailySalesPojo> dailySalesPojosList = dailySalesDao.getAll();
        assertEquals(1, dailySalesPojosList.size());
    }

    public void addDailySales() {
        DailySalesPojo dailySalesPojo = new DailySalesPojo();
        dailySalesPojo.setLocalDate(date);
        dailySalesPojo.setInvoiced_orders_count(3);
        dailySalesPojo.setInvoiced_items_count(10);
        dailySalesPojo.setTotal_revenue(1000.0);
        dailySalesDao.insert(dailySalesPojo);
    }

}
