package com.increff.employee.dto;

import com.increff.employee.api.ApiException;
import com.increff.employee.api.BrandApi;
import com.increff.employee.api.InventoryApi;
import com.increff.employee.api.ProductApi;
import com.increff.employee.flowApi.ProductFlowAPi;
import com.increff.employee.model.data.ProductData;
import com.increff.employee.model.form.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductDto extends HelperDto {
    @Autowired
    private ProductApi productApi;
    @Autowired
    private BrandApi brandApi;
    @Autowired
    private InventoryApi inventoryApi;
    @Autowired
    private ProductFlowAPi productFlowAPi;


    public List<List<String>> addList(List<ProductForm> formList) throws ApiException {
        if (formList.size() > 5000) {
            throw new ApiException("Max Upload Limit: 5000");
        }
        List<List<String>> errorList = new ArrayList<>();
        for (ProductForm form : formList) {
            try {
                add(form);
            } catch (ApiException e) {
                List<String> temp = new ArrayList<>();
                temp.add(form.getBrand());
                temp.add(form.getCategory());
                temp.add(form.getBarcode());
                temp.add(form.getName());
                temp.add(e.getMessage());
                errorList.add(temp);
            }
        }
        return errorList;
    }

    public void add(ProductForm productForm) throws ApiException {
        validateProductForm(productForm);
        normalizeProductForm(productForm);
        productApi.getPojoByBarcode(productForm.getBarcode());
        ProductPojo productPojo = convertFormToPojo(productForm);
        productApi.getCheck(productPojo.getBrand_category(), productPojo.getName());
        productFlowAPi.insert(productPojo);
    }

    public List<ProductData> getAll() throws ApiException {
        List<ProductPojo> list = productApi.getAll();
        List<ProductData> list1 = new ArrayList<ProductData>();
        for (ProductPojo productPojo : list) {
            ProductData productData = convertProductPojoToData(productPojo);
            BrandPojo brandPojo = brandApi.getCheck(productPojo.getBrand_category());
            String brandName = brandPojo.getBrand();
            String categoryName = brandPojo.getCategory();
            productData.setBrand(brandName);
            productData.setCategory(categoryName);
            list1.add(productData);
        }
        return list1;
    }

    public void update(Integer id, ProductForm productForm) throws ApiException {
        validateProductEditForm(productForm);
        normalizeProductForm(productForm);
        ProductPojo productPojo1 = productApi.getPojoFromId(id);
        String initialName = productPojo1.getName();
        if (!Objects.equals(initialName, productForm.getName())) {
            productApi.getCheck(productPojo1.getBrand_category(), productForm.getName());
        }
        productPojo1.setMrp(productForm.getMrp());
        productPojo1.setName(productForm.getName());
        productApi.update(productPojo1);
    }

    public Integer getQuantityFromInventoryByPID(Integer id) throws ApiException {
        return inventoryApi.getById(id).getQuantity();
    }

    public ProductData getDataFromBarcode(String barcode) throws ApiException {
        ProductPojo pojo = productApi.getByBarcode(barcode);
        return convertProductPojoToData(pojo);
    }

    public ProductData getDataFromId(Integer id) throws ApiException {
        ProductPojo productPojo = productApi.getPojoFromId(id);
        return convertProductPojoToData(productPojo);
    }

    private ProductPojo convertFormToPojo(ProductForm form) throws ApiException {
        BrandPojo brandPojo = brandApi.getBrandCat(form.getBrand(), form.getCategory());
        Integer brandCategory_Id = brandPojo.getId();
        ProductPojo productPojo = new ProductPojo();
        productPojo.setMrp(form.getMrp());
        productPojo.setBarcode(form.getBarcode());
        productPojo.setName(form.getName());
        productPojo.setBrand_category(brandCategory_Id);
        return productPojo;
    }


}
