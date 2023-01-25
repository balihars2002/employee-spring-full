package com.increff.employee.dto;

import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderForm;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

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
        Integer id = orderPojo.getId();
       // List<OrderItemPojo> orderItemPojo = new ArrayList<OrderItemPojo>();
        for(OrderItemForm a:orderForm.getOrderItemFormList()){
            orderItemDto.add(a,id);
        }
    }

    @Transactional
    public void delete(Integer id) {
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

        List<OrderItemData> orderItemDataList = orderItemDto.viewAlLOrderItemsWithGivenOrderId(orderPojo.getId());
        orderData.setOrderItemDataList(orderItemDataList);
       // orderData.setLocalDateTime(orderPojo.getDateTime());
        return orderData;
    }
    public OrderPojo convertFormToPojo(OrderForm orderForm){
        OrderPojo orderPojo = new OrderPojo();
        LocalDateTime localDateTime = LocalDateTime.now();
//        Date date = Date.UTC();
        orderPojo.setDateTime(localDateTime);
        return orderPojo;
    }
}
