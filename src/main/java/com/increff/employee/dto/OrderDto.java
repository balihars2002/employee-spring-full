package com.increff.employee.dto;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.OrderForm;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderApi;
import com.increff.employee.service.OrderItemApi;
import com.increff.employee.service.ProductService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Vector;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
//public class CurrentDateTimeExample1 {
//    public static void main(String[] args) {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println(dtf.format(now));
//    }
//}
@Service
public class OrderDto {

    @Autowired
    private OrderApi orderApi;
//    @Autowired
//    private ProductService productApi;
//    @Autowired
//    private OrderItemApi orderItemApi;
    @Autowired
    private OrderItemDto orderItemDto;
    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderForm orderForm) throws ApiException{
        OrderPojo orderPojo = convertFormToPojo(orderForm);
        orderApi.add(orderPojo);
        int id = orderPojo.getId();
        Vector<OrderItemPojo> orderItemPojo;
        for(OrderItemForm a:orderForm.getOrderItemFormvector()){
            orderItemDto.add(a,id);
        }
    }

    public OrderPojo convertFormToPojo(OrderForm orderForm){
        OrderPojo orderPojo = new OrderPojo();
        LocalDateTime localDateTime = LocalDateTime.now();
        orderPojo.setDateTime(localDateTime);
        return orderPojo;
    }

}
