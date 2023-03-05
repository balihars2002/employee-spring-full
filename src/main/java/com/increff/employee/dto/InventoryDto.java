package com.increff.employee.dto;

import com.increff.employee.api.ApiException;
import com.increff.employee.api.BrandApi;
import com.increff.employee.api.InventoryApi;
import com.increff.employee.api.ProductApi;
import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.form.InventoryForm;
import com.increff.employee.model.form.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<List<String>> addList(List<InventoryForm> formList) throws ApiException{
        if(formList.size()>5000){
            throw new ApiException("Max Upload Limit: 5000");
        }
        List<List<String>> errorList = new ArrayList<>();
        for(InventoryForm form : formList) {
            try {
                add(form);
            } catch(ApiException e)
            {
                List<String> temp = new ArrayList<>();
                temp.add(form.getBarcode());
                temp.add(form.getQuantity().toString());
                temp.add(e.getMessage());
                errorList.add(temp);
            }
        }
        return errorList;
    }

    public void add(InventoryForm form) throws ApiException {
        if (form.getQuantity() < 0) {
            throw new ApiException("Product quantity can not be negative");
        }
        Integer id = productApi.getByBarcode(form.getBarcode()).getId();
        InventoryPojo pojo1 = inventoryApi.getByProductId(id);
        if (pojo1 != null) {
            increaseOrDecreaseInventory(pojo1.getId(), form.getQuantity(), true);
        } else {
            InventoryPojo pojo = convertFormToPojo(form);
            inventoryApi.add(pojo);
        }
    }

    public List<InventoryData> getAll() throws ApiException {
        List<InventoryPojo> list = inventoryApi.getAll();
        List<InventoryData> list1 = new ArrayList<InventoryData>();
        for (InventoryPojo p : list) {
            InventoryData inventoryData = convertInventoryPojoToData(p);
            list1.add(inventoryData);
        }
        return list1;
    }


    public void update(Integer id, InventoryForm inventoryForm) throws ApiException {
        if (inventoryForm.getQuantity() < 0) {
            throw new ApiException("Quantity cannot be negative");
        }
        InventoryPojo inventoryPojo1 = inventoryApi.getById(id);
        if (inventoryPojo1 == null) {
            throw new ApiException("The product does not exist");
        }
        inventoryApi.updateInv(inventoryPojo1, inventoryForm.getQuantity());
    }


    public void increaseOrDecreaseInventory(Integer id, Integer changeQuantityBy, Boolean increase) throws ApiException {
        if (increase) {
            changeQuantityBy = -changeQuantityBy;
        }
        InventoryPojo inventoryPojo = inventoryApi.getById(id);
        Integer initialQuantity = inventoryPojo.getQuantity();
        if ((initialQuantity - changeQuantityBy) < 0) {
            throw new ApiException("Quantity cannot be negative");
        }
        inventoryApi.updateInv(inventoryPojo, (initialQuantity - changeQuantityBy));
    }

    public InventoryData getDataById(Integer id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryApi.getById(id);
        if (inventoryPojo == null) {
            throw new ApiException("Product with given id does not exist.");
        }
        return convertInventoryPojoToData(inventoryPojo);
    }

    public InventoryData convertInventoryPojoToData(InventoryPojo inventoryPojo) throws ApiException {
        InventoryData data = new InventoryData();
        ProductPojo productPojo = productApi.getById(inventoryPojo.getProductId());
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

    public InventoryPojo convertFormToPojo(InventoryForm form) throws ApiException {
        ProductPojo productPojo = productApi.getByBarcode(form.getBarcode());
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setProductId(productPojo.getId());
        inventoryPojo.setQuantity(form.getQuantity());
        return inventoryPojo;
    }

}
