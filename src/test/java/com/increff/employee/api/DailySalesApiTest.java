package com.increff.employee.api;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.pojo.DailySalesPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DailySalesApiTest extends AbstractUnitTest {
    @Autowired
    private DailySalesApi dailySalesApi;
    private final LocalDate date = LocalDate.now().minusDays(1);

    @Test
    public void addTest() throws ApiException{
        addDailySales();
        List<DailySalesPojo> dailySalesPojos = dailySalesApi.getAll();
        assertEquals(1,dailySalesPojos.size());
    }

    @Test
    public void getAllTest() throws ApiException{
        addDailySales();
        List<DailySalesPojo> dailySalesPojos = dailySalesApi.getAll();
        assertEquals(1,dailySalesPojos.size());
    }


    public void addDailySales() throws ApiException {
        DailySalesPojo dailySalesPojo = new DailySalesPojo();
        dailySalesPojo.setLocalDate(date);
        dailySalesPojo.setInvoiced_orders_count(3);
        dailySalesPojo.setInvoiced_items_count(10);
        dailySalesPojo.setTotal_revenue(1000.0);
        dailySalesApi.add(dailySalesPojo);
    }
}
