package com.increff.employee.dto;

import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.model.InventoryData;

import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandApi;
import com.increff.employee.service.InventoryApi;
import com.increff.employee.service.ProductApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryDto {

    @Autowired
    private ProductApi productApi;
    @Autowired
    private BrandApi brandApi;
    @Autowired
    private InventoryApi inventoryApi;
    @Transactional(rollbackOn = ApiException.class)
    public void addDto(InventoryForm form) throws ApiException {
        InventoryPojo pojo = convertFormToPojo(form);
        inventoryApi.addService(pojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void deleteInventoryById(Integer id) throws ApiException{
        inventoryApi.deleteService(id);
    }

      @Transactional
    public List<InventoryData> getAllDto() throws ApiException{
          List<InventoryPojo> list= inventoryApi.selectAllFromService();
          List<InventoryData> list1 = new ArrayList<InventoryData>();
          for(InventoryPojo p:list){
              list1.add(convertPojoToData(p));
          }
          return list1;
      }
      @Transactional(rollbackOn = ApiException.class)
      public void updateInv(Integer id,InventoryForm inventoryForm) throws ApiException{
          InventoryPojo inventoryPojo1 = inventoryApi.getPojoFromId(id);
          if(inventoryPojo1 == null){
              throw new ApiException("The product does not exist");
          }
          inventoryPojo1.setQuantity(inventoryForm.getQuantity());
          inventoryApi.updateInv(inventoryPojo1);
      }

      @Transactional(rollbackOn = ApiException.class)
      public void increaseOrDecreaseInventory(Integer id,Integer changeQuantityBy,Boolean increase){
            if(increase) {
                changeQuantityBy = -changeQuantityBy;
            }
           InventoryPojo inventoryPojo = inventoryApi.getPojoFromId(id);
            int initialQuantity = inventoryPojo.getQuantity();
            inventoryPojo.setQuantity(initialQuantity - changeQuantityBy);
            inventoryApi.updateInv(inventoryPojo);
      }
      public InventoryData getDataFromId(Integer id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryApi.getPojoFromId(id);
        if(inventoryPojo == null){
            throw new ApiException("Product with given id does not exist.");
        }
        return convertPojoToData(inventoryPojo);
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
          InventoryData d = new InventoryData();
          ProductPojo productPojo= productApi.givePojoById(inventoryPojo.getProductId());
          //  get brand and category from id
          BrandPojo brandPojo = brandApi.getCheck(productPojo.getBrand_category());
          d.setId(inventoryPojo.getId());
          d.setProductId(productPojo.getId());
          d.setQuantity(inventoryPojo.getQuantity());
          d.setMrp(productPojo.getMrp());
          d.setName(productPojo.getName());
          d.setBarcode(productPojo.getBarcode());
          d.setBrand(brandPojo.getBrand());
          d.setCategory(brandPojo.getCategory());
          return d;
      }
        public InventoryPojo convertFormToPojo(InventoryForm form) throws ApiException{
            ProductPojo productPojo = productApi.getPojoFromBarcode(form.getBarcode());
            if(productPojo == null){
                throw new ApiException("The Product with Id does not exist.");
            }
            InventoryPojo inventoryPojo = new InventoryPojo();
            inventoryPojo.setProductId(productPojo.getId());
            inventoryPojo.setQuantity(form.getQuantity());
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
