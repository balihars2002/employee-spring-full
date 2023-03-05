package com.increff.employee.dto;

import com.increff.employee.api.ApiException;
import com.increff.employee.api.OrderApi;
import com.increff.employee.api.OrderItemApi;
import com.increff.employee.api.ProductApi;
import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.dto.HelperDto.convertOrderItemFormToPojo;
import static com.increff.employee.dto.HelperDto.convertOrderItemPojoToData;

@Service
public class OrderItemDto {

    @Autowired
    private OrderItemApi orderItemApi;
    @Autowired
    private ProductApi productApi;
    @Autowired
    private OrderApi orderApi;


    public void add(OrderItemForm orderItemForm, Integer orderId) throws ApiException {
        OrderItemPojo orderItemPojo = convertOrderItemFormToPojo(orderItemForm, orderId);
        ProductPojo productPojo = productApi.getByBarcode(orderItemForm.getBarcode());
        if (orderItemForm.getSellingPrice() > productPojo.getMrp()) {
            throw new ApiException("Selling Price cannot be greater than MRP");
        } else {
            orderItemPojo.setProductId(productPojo.getId());
            orderItemPojo.setSellingPrice(orderItemForm.getSellingPrice());
            orderItemApi.add(orderItemPojo);
        }
    }

    public void deleteByProductId(Integer product_id) {
        orderItemApi.deleteByProductId(product_id);
    }

    public void deleteById(Integer id) throws ApiException {
        OrderItemPojo orderItemPojo = orderItemApi.getById(id);
        Integer orderId = orderItemPojo.getOrderId();
        List<OrderItemPojo> orderItemPojoList = orderItemApi.getByOrderId(orderId);
        if (orderItemPojoList.size() <= 1) {
            throw new ApiException("Order cannot be Empty");
        } else {
            orderItemApi.deleteById(id);
        }
    }

    public void deleteByOrderId(Integer order_id) {
        orderItemApi.deleteByProductId(order_id);
    }

    public List<OrderItemData> viewAlLOrderItems() throws ApiException {
        List<OrderItemData> list = new ArrayList<OrderItemData>();
        List<OrderItemPojo> list1 = orderItemApi.getAll();
        for (OrderItemPojo pojo : list1) {
            list.add(convertOrderItemPojoToData(pojo));
        }
        return list;
    }

    public List<OrderItemData> viewAlLOrderItemsWithGivenOrderId(Integer orderId) throws ApiException {
        List<OrderItemData> list = new ArrayList<OrderItemData>();
        List<OrderItemPojo> list1 = orderItemApi.getByOrderId(orderId);
        for (OrderItemPojo pojo : list1) {
            OrderItemData orderItemData = convertOrderItemPojoToData(pojo);
            ProductPojo productPojo = productApi.getPojoFromId(pojo.getProductId());
            orderItemData.setBarcode(productPojo.getBarcode());
            orderItemData.setProductName(productPojo.getName());
            list.add(orderItemData);
        }
        return list;
    }

    public OrderItemData getDataById(Integer id) throws ApiException {
        OrderItemPojo orderItemPojo = orderItemApi.getById(id);
        OrderItemData orderItemData = convertOrderItemPojoToData(orderItemPojo);
        ProductPojo productPojo = productApi.getPojoFromId(orderItemPojo.getProductId());
        orderItemData.setBarcode(productPojo.getBarcode());
        orderItemData.setProductName(productPojo.getName());
        return orderItemData;
    }

    public void update(Integer id, OrderItemForm orderItemForm) throws ApiException {
        orderItemApi.update(id, orderItemForm.getQuantity(), orderItemForm.getBarcode(), orderItemForm.getSellingPrice());
        OrderItemPojo orderItemPojo = orderItemApi.getById(id);
        orderApi.update(orderItemPojo.getOrderId());
    }


}
