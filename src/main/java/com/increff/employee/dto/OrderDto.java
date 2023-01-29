package com.increff.employee.dto;

import com.increff.employee.model.*;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderApi;
import com.increff.employee.spring.SecurityConfig;
import com.increff.employee.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;

@Service
public class OrderDto {

    @Autowired
    private OrderApi orderApi;
    @Autowired
    private ProductDto productDto;
//    @Autowired
//    private ProductService productApi;
//    @Autowired
//    private OrderItemApi orderItemApi;
    @Autowired
    private OrderItemDto orderItemDto;
    private static Logger logger = Logger.getLogger(SecurityConfig.class);

//    @Autowired
//    private SalesReportData salesReportData;
//    @Autowired
//    private SalesReportDto salesReportDto;

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
        orderData.setDate(orderPojo.getDate());
       // orderData.setLocalDateTime(orderPojo.getDateTime());
        return orderData;
    }
    public OrderPojo convertFormToPojo(OrderForm orderForm){
        OrderPojo orderPojo = new OrderPojo();
        LocalDateTime localDateTime = LocalDateTime.now();
        long millis = System.currentTimeMillis();
        //Date date = new Date(millis);
        DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm:ss ");

        // Creating date from milliseconds
        // using Date() constructor
        Date date = new Date(millis);
        orderPojo.setDateTime(localDateTime);
        orderPojo.setDate(simple.format(date));
        return orderPojo;
    }

    public List<SalesReportData> getReport(LocalDate startDate,LocalDate endDate,String brand,String category) throws ApiException{
        logger.info("Entered Order DTO");

        List<OrderPojo> orderPojoList = orderApi.selectInDate(startDate, endDate);
        HashMap<List<String>,Integer> mapQuantity = new HashMap<List<String>,Integer>();
        HashMap<List<String>,Double> mapRevenue = new HashMap<List<String>,Double>();

        logger.info(brand + " " + category);

        for(OrderPojo pojo:orderPojoList){
            List<OrderItemData> orderItemDataList = orderItemDto.viewAlLOrderItemsWithGivenOrderId(pojo.getId());
            for(OrderItemData data: orderItemDataList){
                ProductData productData = productDto.getDataFromId(data.getProductId());
                if(!StringUtil.isEmpty(category) && !StringUtil.isEmpty(brand)) {
                    if( brand.equals(productData.getBrand()) && category.equals(productData.getCategory())) {
                        logger.info("Entered 1");
                        List<String> tempList = new ArrayList<>();
                        tempList.add(productData.getBrand());
                        tempList.add(productData.getCategory());

                        if (mapQuantity.containsKey(tempList)) {
                            Integer t = mapQuantity.get(tempList);
                            mapQuantity.put(tempList, t + data.getQuantity());
                            Double d = mapRevenue.get(tempList);
                            Double mrp = productData.getMrp();
                            mapRevenue.put(tempList, d + data.getQuantity() * mrp);
                        } else {
                            mapQuantity.put(tempList, data.getQuantity());
                            mapRevenue.put(tempList, productData.getMrp() * data.getQuantity());
                        }
                    }
                }
                else if (!StringUtil.isEmpty(category)){
                    if(category.equals(productData.getCategory())) {
                        logger.info("Entered 2");
                        List<String> tempList = new ArrayList<>();
                        tempList.add(productData.getBrand());
                        tempList.add(productData.getCategory());

                        if (mapQuantity.containsKey(tempList)) {
                            Integer t = mapQuantity.get(tempList);
                            mapQuantity.put(tempList, t + data.getQuantity());
                            Double d = mapRevenue.get(tempList);
                            Double mrp = productData.getMrp();
                            mapRevenue.put(tempList, d + data.getQuantity() * mrp);
                        } else {
                            mapQuantity.put(tempList, data.getQuantity());
                            mapRevenue.put(tempList, productData.getMrp() * data.getQuantity());
                        }
                    }
                }
                else if (!StringUtil.isEmpty(brand)){
                    if(brand.equals(productData.getBrand())) {
                        logger.info("Entered 3");
                        List<String> tempList = new ArrayList<>();
                        tempList.add(productData.getBrand());
                        tempList.add(productData.getCategory());

                        if (mapQuantity.containsKey(tempList)) {
                            Integer t = mapQuantity.get(tempList);
                            mapQuantity.put(tempList, t + data.getQuantity());
                            Double d = mapRevenue.get(tempList);
                            Double mrp = productData.getMrp();
                            mapRevenue.put(tempList, d + data.getQuantity() * mrp);
                        } else {
                            mapQuantity.put(tempList, data.getQuantity());
                            mapRevenue.put(tempList, productData.getMrp() * data.getQuantity());
                        }
                    }
                }
                else {
                    logger.info("Entered 4");
                    List<String> tempList = new ArrayList<>();
                    tempList.add(productData.getBrand());
                    tempList.add(productData.getCategory());

                    if (mapQuantity.containsKey(tempList)) {
                        Integer t = mapQuantity.get(tempList);
                        mapQuantity.put(tempList, t + data.getQuantity());
                        Double d = mapRevenue.get(tempList);
                        Double mrp = productData.getMrp();
                        mapRevenue.put(tempList, d + data.getQuantity() * mrp);
                    } else {
                        mapQuantity.put(tempList, data.getQuantity());
                        mapRevenue.put(tempList, productData.getMrp() * data.getQuantity());
                    }
                }
            }
        }
        List<SalesReportData> salesReportDataList = new ArrayList<>();
        for(Map.Entry m : mapQuantity.entrySet()){
            SalesReportData salesReportData1 = new SalesReportData();
            List<String> tempList = (List<String>) m.getKey();
            salesReportData1.setBrand(tempList.get(0));
            salesReportData1.setCategory(tempList.get(1));
            salesReportData1.setQuantity((Integer) m.getValue());
            Double d = mapRevenue.get(tempList);
            salesReportData1.setRevenue(d);
            salesReportDataList.add(salesReportData1);
            //  System.out.println(m.getKey()+" "+m.getValue());
        }
        return salesReportDataList;
    }
}
