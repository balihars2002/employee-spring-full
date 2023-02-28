package com.increff.employee.dto;

import com.increff.employee.flowApi.OrderFlowApi;
import com.increff.employee.model.data.OrderData;
import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.form.InvoiceForm;
import com.increff.employee.model.form.OrderForm;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.api.*;
import com.increff.employee.spring.SecurityConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.increff.employee.dto.HelperDto.*;

@Service
public class OrderDto {

    @Autowired
    private OrderItemDto orderItemDto;
    @Autowired
    private OrderApi orderApi;
    @Autowired
    private OrderItemApi orderItemApi;
    @Autowired
    private ProductApi productApi;
    @Autowired
    private OrderFlowApi orderFlowApi;
    @Autowired
    private InventoryApi inventoryApi;

    @Value("${invoice.url}")
    private String invoiceUrl;

    private final static Logger logger = Logger.getLogger(SecurityConfig.class);


    public void add(List<OrderItemForm> orderItemFormList) throws ApiException{
        OrderForm orderForm = new OrderForm();
        orderForm.setOrderItemFormList(orderItemFormList);
        OrderPojo orderPojo = convertFormToPojo(orderForm);
        orderFlowApi.add(orderPojo,orderItemFormList);
    }

    public void delete(Integer id) {
        orderApi.delete(id);
    }

    public void generateInvoice(Integer id) throws ApiException {
        orderApi.generateInvoice(id);
    }
//    public void updateList(OrderForm form) throws ApiException {
////        OrderPojo orderPojo = orderApi.selectById()
////        BrandPojo brandPojo1 = brandService.getBrandCat(brandPojo.getBrand(),brandPojo.getCategory());
////        if(brandPojo1 != null){
////            throw new ApiException("Brand and Category already exist");
////        }
////        normalizeBrand(brandPojo);
////        BrandPojo updated = getCheckFromService(id);
////        updated.setCategory(brandPojo.getCategory());
////        updated.setBrand(brandPojo.getBrand());
////        brandService.update(updated);
//        orderApi.update(orderPojo.);
//    }

    public List<OrderData> viewAlLOrder() throws ApiException {
        List<OrderData> list = new ArrayList<OrderData>();
        List<OrderPojo> list1 = orderApi.selectAll();
        for(OrderPojo pojo:list1){
            list.add(convertPojoToData(pojo));
        }
        return list;
    }
    public List<OrderItemData> viewOrderItemsInOrder(Integer id) throws ApiException {
        List<OrderItemPojo> orderItemPojoList = orderItemApi.selectSome(id);
        List<OrderItemData> orderItemDataList = new ArrayList<>();
        for(OrderItemPojo orderItemPojo:orderItemPojoList){
            OrderItemData orderItemData = convertOrderItemPojoToData(orderItemPojo);
            ProductPojo productPojo = productApi.getPojoFromId(orderItemPojo.getProductId());
            orderItemData.setBarcode(productPojo.getBarcode());
            orderItemDataList.add(orderItemData);
        }
        return orderItemDataList;
    }
    
    public OrderData convertPojoToData(OrderPojo orderPojo) throws ApiException {
        OrderData orderData = new OrderData();
        orderData.setId(orderPojo.getId());
        List<OrderItemData> orderItemDataList = orderItemDto.viewAlLOrderItemsWithGivenOrderId(orderPojo.getId());
        orderData.setOrderItemDataList(orderItemDataList);
        ZonedDateTime addDate = orderPojo.getAddDate();
        ZonedDateTime updateDate = orderPojo.getUpdateDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss");
        String add = addDate.format(formatter);
        String update = updateDate.format(formatter);
        orderData.setOrderAddDate(add);
        orderData.setOrderUpdatedDate(update);
        orderData.setInvoiceGenerated(orderPojo.getInvoiceGenerated());
        return orderData;
    }
    public OrderPojo convertFormToPojo(OrderForm orderForm){
        OrderPojo orderPojo = new OrderPojo();
        LocalDate date = LocalDate.now();
        orderPojo.setOrderLocalTime(date);
        return orderPojo;
    }


    public ResponseEntity<byte[]> getPDF(Integer id) throws Exception {
        InvoiceForm invoiceForm = generateInvoiceForOrder(id);
        RestTemplate restTemplate = new RestTemplate();
        byte[] contents = Base64.getDecoder().decode(restTemplate.postForEntity(invoiceUrl, invoiceForm, byte[].class).getBody());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "invoice.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        orderApi.generateInvoice(id);
        return response;
    }

    public InvoiceForm generateInvoiceForOrder(Integer orderId) throws ApiException
    {
        InvoiceForm invoiceForm = new InvoiceForm();
        OrderPojo orderPojo = orderApi.selectById(orderId);
        invoiceForm.setOrderId(orderPojo.getId());
        invoiceForm.setId(orderId);

        List<OrderItemData> orderItemDataList = orderItemDto.viewAlLOrderItemsWithGivenOrderId(orderId);
        double totalAmount = 0.0;
        for(OrderItemData data: orderItemDataList){
            totalAmount += (data.getSellingPrice())*(data.getQuantity());
        }
        invoiceForm.setTotalCost(totalAmount);
        invoiceForm.setOrderItemDataList(orderItemDataList);
        LocalDate localDate = orderPojo.getOrderLocalTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = localDate.format(dateTimeFormatter);
        invoiceForm.setAddDate(formattedDateTime);
        System.out.println(" formatted Date : : " + formattedDateTime);
        return invoiceForm;
    }

}
