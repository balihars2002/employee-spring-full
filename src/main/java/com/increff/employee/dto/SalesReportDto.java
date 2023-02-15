package com.increff.employee.dto;

import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.data.ProductData;
import com.increff.employee.model.data.SalesReportData;
import com.increff.employee.model.form.SalesReportForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.*;
import com.increff.employee.spring.SecurityConfig;
import com.increff.employee.util.CsvFileGenerator;
import com.increff.employee.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.increff.employee.dto.HelperDto.convertOrderItemPojoToData;
import static com.increff.employee.dto.HelperDto.convertProductPojoToData;

@Service
public class SalesReportDto {
//    @Autowired
//    private OrderDto orderDto;
    @Autowired
    private OrderApi orderApi;
    @Autowired
    private ProductApi productApi;
    @Autowired
    private OrderItemApi orderItemApi;
    @Autowired
    private BrandApi brandApi;
    @Autowired
    private CsvFileGenerator csvFileGenerator;

    private final static Logger logger = Logger.getLogger(SecurityConfig.class);



    public List<SalesReportData> get(SalesReportForm form) throws ApiException {
        logger.info("Entered DTO");
//       if (form.getBrand() == null && form.getCategory() == null){

        // Handle Null Dates
        if(form.getStartingDate() == null || StringUtil.isEmpty(form.getStartingDate())) {
            form.setStartingDate("1750-01-01");
        }

        if(form.getEndingDate() == null || StringUtil.isEmpty(form.getEndingDate())) {
            form.setEndingDate(LocalDate.now().toString());
        }

        // Parse String to LocalDate
        LocalDate startDateTime = LocalDate.parse(form.getStartingDate());
        LocalDate endDateTime = LocalDate.parse(form.getEndingDate());

        return getReport(startDateTime, endDateTime, form.getBrand(), form.getCategory());
//
    }
    public List<SalesReportData> getReport(LocalDate startDate, LocalDate endDate, String brand, String category) throws ApiException{
        List<OrderPojo> orderPojoList = orderApi.selectInDate(startDate, endDate);
        System.out.println("the size of the list of orders : " + orderPojoList.size());
        HashMap<List<String>,Integer> mapQuantity = new HashMap<List<String>,Integer>();
        HashMap<List<String>,Double> mapRevenue = new HashMap<List<String>,Double>();

        logger.info(brand + " " + category);

        for(OrderPojo pojo:orderPojoList){

            List<OrderItemPojo> orderItemPojoList = orderItemApi.selectSome(pojo.getId());
            List<OrderItemData> orderItemDataList = convertPojoListToDataList(orderItemPojoList);
            for(OrderItemData data: orderItemDataList) {
                if (data.getProductId() != null) {
//                    logger.info("into atleast 1 order item");
                    ProductPojo productPojo = productApi.givePojoById(data.getProductId());
                    ProductData productData = convertProductPojoToData(productPojo);
                    BrandPojo brandPojo= brandApi.getCheck(productPojo.getBrand_category());
                    if(brandPojo == null){
                        throw new ApiException("ID is not valid");
                    }
                    String brandName= brandPojo.getBrand();
                    String categoryName= brandPojo.getCategory();
                    productData.setBrand(brandName);
                    productData.setCategory(categoryName);
                    logger.info(productPojo.getName() + " " + productPojo.getBarcode());
                    if (!StringUtil.isEmpty(category) && !StringUtil.isEmpty(brand)) {
                        if (brand.equals(productData.getBrand()) && category.equals(productData.getCategory())) {
//                        logger.info("Entered 1");
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
                    } else if (!StringUtil.isEmpty(category)) {
                        if (category.equals(productData.getCategory())) {
//                            logger.info("Entered 2");
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
                    } else if (!StringUtil.isEmpty(brand)) {
                        if (brand.equals(productData.getBrand())) {
//                            logger.info("Entered 3");
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
                    } else {
//                        logger.info("Entered 4");
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

//    public void generateCsv(HttpServletResponse response) throws IOException {
//        response.setContentType("text/csv");
//        response.addHeader("Content-Disposition", "attachment; filename=\"salesReport.csv\"");
//        List<SalesReportData> salesReportDataList =
//        csvFileGenerator.writeSalesToCsv(salesList, response.getWriter());
//        salesList.clear();
//    }

    public List<OrderItemData> convertPojoListToDataList(List<OrderItemPojo> orderItemPojoList) throws ApiException {
        List<OrderItemData> list = new ArrayList<OrderItemData>();
        for(OrderItemPojo pojo:orderItemPojoList){
            OrderItemData orderItemData = convertOrderItemPojoToData(pojo);
            ProductPojo productPojo = productApi.getPojoFromId(pojo.getProductId());
            orderItemData.setBarcode(productPojo.getBarcode());
            list.add(orderItemData);
        }
        return list;
    }
}
