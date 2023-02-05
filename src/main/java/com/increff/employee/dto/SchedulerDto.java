package com.increff.employee.dto;

import com.increff.employee.model.data.SchedulerData;

import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.pojo.SchedulerPojo;
import com.increff.employee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableScheduling
public class SchedulerDto {

    @Autowired
    private SchedulerApi schedulerApi;
    @Autowired
    private OrderApi orderApi;
    @Autowired
    private OrderItemApi orderItemApi;
    @Autowired
    private ProductApi productApi;

    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleTask() throws ApiException {

        SchedulerPojo schedulerPojo = new SchedulerPojo();
//        System.out.println("hello ");
        schedulerPojo.setTotal_revenue((Double) 2.0);
        //get date to get the query to store in the scheduler pojo
        LocalDate date = LocalDate.now();

        //
        List<OrderPojo> orderPojoList = new ArrayList<>();
        orderPojoList = orderApi.getOrdersForScheduler(date);
        schedulerPojo.setLocalDate(date);
        schedulerPojo.setInvoiced_orders_count(orderPojoList.size());
        Double revenue = (double) 0;
        Integer totalOrderItems = 0;
        for(OrderPojo pojo : orderPojoList){
            List<OrderItemPojo> orderItemPojos = new ArrayList<>();
            orderItemPojos = orderItemApi.selectSome(pojo.getId());
            for(OrderItemPojo orderItemPojo : orderItemPojos){
                totalOrderItems += orderItemPojo.getQuantity();
                ProductPojo productPojo = productApi.givePojoById(orderItemPojo.getProductId());
                revenue += ( orderItemPojo.getQuantity() * productPojo.getMrp() );

            }
        }
        schedulerPojo.setTotal_revenue(revenue);
        schedulerPojo.setInvoiced_items_count(totalOrderItems);
        schedulerApi.add(schedulerPojo);
    }

    public List<SchedulerData> getAllData() throws ApiException {
        List<SchedulerData> list = new ArrayList<SchedulerData>();
        List<SchedulerPojo> list1 = schedulerApi.selectAll();
        for(SchedulerPojo pojo:list1){
            list.add(convertPojoToData(pojo));
        }
        return list;
    }

    public SchedulerData convertPojoToData(SchedulerPojo pojo){
        SchedulerData data = new SchedulerData();
        data.setDate(pojo.getLocalDate().toString());
        data.setInvoiced_items_count(pojo.getInvoiced_items_count());
        data.setInvoiced_orders_count(pojo.getInvoiced_orders_count());
        data.setTotal_revenue(pojo.getTotal_revenue());
        return data;
    }
}
