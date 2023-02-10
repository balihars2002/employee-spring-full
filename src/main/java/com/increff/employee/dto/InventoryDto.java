package com.increff.employee.dto;

import com.increff.employee.model.form.InventoryForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.model.data.InventoryData;

import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandApi;
import com.increff.employee.service.InventoryApi;
import com.increff.employee.service.ProductApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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


    public void addDto(InventoryForm form) throws ApiException {
        if(form.getQuantity()<0){
            throw new ApiException("The product Quantity cannot be negative");
        }
        Integer id = productApi.getPojoFromBarcode(form.getBarcode()).getId();
        InventoryPojo pojo1 = inventoryApi.getPojoFromProductId(id);
        if(pojo1 != null){
            increaseOrDecreaseInventory(pojo1.getId(),form.getQuantity(),true);
        }
        else {
            InventoryPojo pojo = convertFormToPojo(form);
            inventoryApi.addService(pojo);
        }
    }

    public void deleteInventoryById(Integer id) throws ApiException{
        inventoryApi.deleteService(id);
    }

    public List<InventoryData> getAllDto() throws ApiException{
          List<InventoryPojo> list= inventoryApi.selectAllFromService();
          List<InventoryData> list1 = new ArrayList<InventoryData>();
          for(InventoryPojo p:list){
              InventoryData inventoryData = convertInventoryPojoToData(p);
              if(inventoryData.getId() != null) {
                  list1.add(inventoryData);
              }
          }
          return list1;
      }


      public void updateInv(Integer id,InventoryForm inventoryForm) throws ApiException{
          InventoryPojo inventoryPojo1 = inventoryApi.getPojoFromId(id);
          if(inventoryPojo1 == null){
              throw new ApiException("The product does not exist");
          }
          inventoryApi.updateInv(inventoryPojo1,inventoryForm.getQuantity());
      }


      public void increaseOrDecreaseInventory(Integer id,Integer changeQuantityBy,Boolean increase) throws ApiException {
            if(increase) {
                changeQuantityBy = -changeQuantityBy;
            }
            InventoryPojo inventoryPojo = inventoryApi.getPojoFromId(id);
            Integer initialQuantity = inventoryPojo.getQuantity();
            inventoryApi.updateInv(inventoryPojo,(initialQuantity - changeQuantityBy));
      }
      public InventoryData getDataFromId(Integer id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryApi.getPojoFromId(id);
        if(inventoryPojo == null){
            throw new ApiException("Product with given id does not exist.");
        }
        return convertInventoryPojoToData(inventoryPojo);
    }

      public InventoryData convertInventoryPojoToData(InventoryPojo inventoryPojo) throws ApiException{
          InventoryData data = new InventoryData();
          ProductPojo productPojo= productApi.givePojoById(inventoryPojo.getProductId());
          if(productPojo == null){
              inventoryApi.deleteService(inventoryPojo.getId());
              return data;
          }
          BrandPojo brandPojo = brandApi.getCheck(productPojo.getBrand_category());
          data.setId(inventoryPojo.getId());
          data.setProductId(productPojo.getId());
          data.setQuantity(inventoryPojo.getQuantity());
          data.setMrp(productPojo.getMrp());
          data.setName(productPojo.getName());
          data.setBarcode(productPojo.getBarcode());
          data.setBrand(brandPojo.getBrand());
          data.setCategory(brandPojo.getCategory());
          return data;
      }
        public InventoryPojo convertFormToPojo(InventoryForm form) throws ApiException{
            ProductPojo productPojo = productApi.getPojoFromBarcode(form.getBarcode());
            InventoryPojo inventoryPojo = new InventoryPojo();
            inventoryPojo.setProductId(productPojo.getId());
            inventoryPojo.setQuantity(form.getQuantity());
            return inventoryPojo;
        }

}
