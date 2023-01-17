package com.increff.employee.dto;

import com.increff.employee.model.InventoryForm;
import com.increff.employee.model.ProductData;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.model.InventoryData;

import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import jdk.incubator.vector.IntVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryDto {

    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;
//    @Transactional(rollbackOn = ApiException.class)
//    public void addto(InventoryForm f) throws ApiException {
//        InventoryPojo p = convertformtopojo(f);
//        inventoryService.addservice(p);
//    }

//    @Transactional(rollbackOn = ApiException.class)
//    public void deleteinventory(int id){
//        inventoryService.deleteservice(id);
//    }

      @Transactional
    public List<InventoryData> getAllDto() throws ApiException{
          List<InventoryPojo> list= inventoryService.selectAllservice();
          List<InventoryData> list1 = new ArrayList<InventoryData>();
          for(InventoryPojo p:list){
              list1.add(convertpojotodata(p));
          }
          return list1;
      }
      @Transactional(rollbackOn = ApiException.class)
      public void updateinv(String barcode){
          ProductPojo p= productService.givepojobybarcode(barcode);
          int pr_id= p.getProId();

      }
      public InventoryData convertpojotodata(InventoryPojo p){
          InventoryData d= new InventoryData();
          ProductPojo pp= productService.givepojobyid(p.getId());
          d.setInvBarcode(pp.getProBarcode());
          d.setInvQuantity(p.getQuantity());
          return d;
      }

//    public InventoryPojo convertformtopojo(InventoryForm f) throws ApiException{
//        InventoryPojo p= new InventoryPojo();
//
//        p.setQuantity(f.getInvQuantity());
//        p.setId(f.getInvId());
//        return p;
//    }

//    public void inventorynormalise(InventoryForm f){
//
//    }
}
