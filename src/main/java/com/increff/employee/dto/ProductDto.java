package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.model.ProductData;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import com.increff.employee.util.StringUtil;
@Service
public class ProductDto extends DtoHelper{
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private InventoryService inventoryService;

    @Transactional(rollbackOn = ApiException.class)
    public void addDto(ProductForm f) throws ApiException {
        normaliseProduct(f);
        ProductPojo p= convertFormToPojo(f);
        if(StringUtil.isEmpty(p.getProBarcode())) {
            throw new ApiException("'Barcode' cannot be empty");
        }
        if(StringUtil.isEmpty(p.getProName())) {
            throw new ApiException("'Brand' cannot be empty");
        }
        productService.insertService(p);

        InventoryPojo ip= new InventoryPojo(p.getProId(),0);
        inventoryService.addService(ip);
    }
    @Transactional
    public ProductPojo getCheckFromService(String barcode) throws ApiException{
        return productService.getCheckByBarcode(barcode);
    }
    @Transactional
    public void deletedToBarcode(String barcode) throws ApiException {
        ProductPojo productPojo = productService.getPojoFromBarcode(barcode);
        inventoryService.deleteService(productPojo.getProId());
        productService.deleteServiceByBarcode(barcode);
    }

    @Transactional
    public List<ProductData> getAllDto() throws ApiException {
        List<ProductPojo> list= productService.selectAllService();
        List<ProductData> list1 = new ArrayList<ProductData>();
        for(ProductPojo p:list){
            list1.add(convertPojoToData(p));
        }
        return list1;
    }
    @Transactional(rollbackOn  = ApiException.class)
    public void updateProduct(String barcode, ProductForm productForm) throws ApiException {
        ProductPojo productPojo= convertFormToPojo(productForm);
        ProductPojo productPojo1 = productService.getPojoFromBarcode(barcode);
        normalizeProduct(productPojo);
        normalizeProduct(productPojo1);
        ProductPojo productPojo2 = productService.getPojoFromId(productPojo1.getProId());
        productPojo2.setProBarcode(productPojo.getProBarcode());
        productPojo2.setProMrp(productPojo.getProMrp());
        productPojo2.setProName(productPojo.getProName());
        productService.update(productPojo2);

    }
    public ProductData getDataFromBarcode(String barcode) throws ApiException {
          ProductPojo productPojo = productService.getPojoFromBarcode(barcode);
          if(productPojo == null){
              throw new ApiException("Product with given barcode does not exist.");
          }
          return convertPojoToData(productPojo);
    }
    public ProductData getDataFromId(int id) throws ApiException {
        ProductPojo productPojo = productService.getPojoFromId(id);
        if(productPojo == null){
            throw new ApiException("Product with given barcode does not exist.");
        }
        return convertPojoToData(productPojo);
    }

    private ProductPojo convertFormToPojo(ProductForm form) throws ApiException{
        BrandPojo brandPojo= brandService.getBrandCat(form.getProBrand(),form.getProCategory());
        if(brandPojo==null){
            throw new ApiException("The brand and category does not exist");
        }
        int brandcat_id= brandPojo.getId();
        ProductPojo p= new ProductPojo(form.getProBarcode(),brandcat_id,form.getProName(),form.getProMrp());
        return p;
    }

    private ProductData convertPojoToData(ProductPojo p) throws ApiException {
        ProductData d = new ProductData();
        d.setProId(p.getProId());
        d.setProBarcode(p.getProBarcode());
        d.setProName(p.getProName());
        d.setProMrp(p.getProMrp());
        BrandPojo bp= brandService.getBrandCatFromiId(p.getProbrandCategory());
        if(bp == null){
            throw new ApiException("ID is not valid");
        }
        String brandname= bp.getBrand();
        String categoryname= bp.getCategory();
        d.setProBrand(brandname);
        d.setProCategory(categoryname);
        return d;
    }
}
