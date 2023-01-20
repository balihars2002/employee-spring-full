package com.increff.employee.dto;

import com.increff.employee.model.InventoryForm;
import com.increff.employee.model.ProductData;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.model.InventoryData;

import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryDto {

    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private InventoryService inventoryService;
    @Transactional(rollbackOn = ApiException.class)
    public void addDto(InventoryForm form) throws ApiException {
        InventoryPojo pojo = convertFormToPojo(form);
        inventoryService.addService(pojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void deleteInventoryById(int id) throws ApiException{
        inventoryService.deleteService(id);
    }

      @Transactional
    public List<InventoryData> getAllDto() throws ApiException{
          List<InventoryPojo> list= inventoryService.selectAllFromService();
          List<InventoryData> list1 = new ArrayList<InventoryData>();
          for(InventoryPojo p:list){
              list1.add(convertPojoToData(p));
          }
          return list1;
      }
      @Transactional(rollbackOn = ApiException.class)
      public void updateInv(int id,InventoryForm inventoryForm) throws ApiException{
          InventoryPojo inventoryPojo = convertFormToPojo(inventoryForm);
          ProductPojo productPojo= productService.getPojoFromId(id);
          InventoryPojo inventoryPojo1 = inventoryService.getPojoFromId(productPojo.getProId());
          if(inventoryPojo1 == null){
              throw new ApiException("The product does not exist");
          }
          inventoryPojo1.setQuantity(inventoryPojo.getQuantity());
          inventoryService.updateInv(inventoryPojo1);
      }
//    @Transactional(rollbackOn = ApiException.class)
//    public void increaseQuantity(String barcode,int addQuantity) throws ApiException{
//        //InventoryPojo inventoryPojo = convertFormToPojo(inventoryForm);
//        ProductPojo productPojo= productService.getPojoFromBarcode(barcode);
//        InventoryPojo inventoryPojo1 = inventoryService.getPojoFromId(productPojo.getProId());
//        if(inventoryPojo1 == null){
//            throw new ApiException("The product does not exist");
//        }
//        int initialQuantity = inventoryPojo1.getQuantity();
//        inventoryPojo1.setQuantity(initialQuantity + addQuantity);
//        inventoryService.updateInv(inventoryPojo1);
//    }

//        @Transactional(rollbackOn = ApiException.class)
//      public void updateInv(String barcode,int quanTity) throws ApiException{
//          ProductPojo productPojo= productService.givePojoByBarcode(barcode);
//          int product_id= productPojo.getProId();
//          InventoryPojo inventoryPojo= new InventoryPojo();
//          inventoryPojo.setQuantity(quanTity);
//
//          inventoryPojo.setId(product_id);
//          inventoryService.updateInv(inventoryPojo);
//      }
      public InventoryData convertPojoToData(InventoryPojo inventoryPojo) throws ApiException{
          InventoryData d= new InventoryData();
          ProductPojo productPojo= productService.givePojoById(inventoryPojo.getId());
          //  get brand and category from id
          BrandPojo brandPojo = brandService.getCheck(productPojo.getProbrandCategory());
          d.setId(productPojo.getProId());
          d.setQuantity(inventoryPojo.getQuantity());
          d.setMrp(productPojo.getProMrp());
          d.setName(productPojo.getProName());
          d.setBarcode(productPojo.getProBarcode());
          d.setBrand(brandPojo.getBrand());
          d.setCategory(brandPojo.getCategory());
          return d;
      }
        public InventoryPojo convertFormToPojo(InventoryForm form) throws ApiException{
            ProductPojo productPojo = productService.getPojoFromBarcode(form.getInvBarcode());
            InventoryPojo inventoryPojo = new InventoryPojo();
            inventoryPojo.setId(productPojo.getProId());
            inventoryPojo.setQuantity(form.getInvQuantity());
            return inventoryPojo;
        }

//       public InventoryPojo convertFormToPojo(InventoryForm f) throws ApiException{
//         InventoryPojo p= new InventoryPojo();
//
//         p.setQuantity(f.getInvQuantity());
//         p.setId(f.getInvId());
//         return p;
//       }

//    public void inventorynormalise(InventoryForm f){
//
//    }
}
