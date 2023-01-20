package com.increff.employee.dto;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.BrandForm;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderForm;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.BrandPojo;
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
import java.util.ArrayList;
import java.util.List;
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

    @Transactional
    public void delete(int id) {
        orderApi.delete(id);
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void updateList(OrderForm form) throws ApiException {
        OrderPojo orderPojo = new OrderPojo();
//        BrandPojo brandPojo1 = brandService.getBrandCat(brandPojo.getBrand(),brandPojo.getCategory());
//        if(brandPojo1 != null){
//            throw new ApiException("Brand and Category already exist");
//        }
//        normalizeBrand(brandPojo);
//        BrandPojo updated = getCheckFromService(id);
//        updated.setCategory(brandPojo.getCategory());
//        updated.setBrand(brandPojo.getBrand());
//        brandService.update(updated);
        orderApi.update(orderPojo);
    }

    @Transactional
    public List<OrderData> viewAlLOrder(){
        List<OrderData> list = new ArrayList<OrderData>();
        List<OrderPojo> list1 = orderApi.selectAll();
        for(OrderPojo pojo:list1){
            list.add(convertPojoToData(pojo));
        }
        return list;
    }

    public OrderData convertPojoToData(OrderPojo orderPojo){
        OrderData orderData = new OrderData();
        orderData.setId(orderPojo.getId());
        orderData.setLocalDateTime(orderPojo.getDateTime());
        return orderData;
    }
    public OrderPojo convertFormToPojo(OrderForm orderForm){
        OrderPojo orderPojo = new OrderPojo();
        LocalDateTime localDateTime = LocalDateTime.now();
        orderPojo.setDateTime(localDateTime);
        return orderPojo;
    }
}
