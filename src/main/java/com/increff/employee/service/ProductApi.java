package com.increff.employee.service;


import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductApi {
    @Autowired
    private ProductDao productDao;

    @Transactional(rollbackOn = ApiException.class)
    public void insertService(ProductPojo productPojo) throws ApiException {
          productDao.insert(productPojo);
    }

    @Transactional
    public void deleteServiceById(Integer id) {
        productDao.delete(id);
    }
    @Transactional
    public void deleteServiceByBarcode(String barcode) {
        productDao.delete(barcode);
    }

    @Transactional
    public List<ProductPojo> selectAllService() {
        return productDao.selectAll();
    }

    @Transactional
    public ProductPojo getPojoFromBarcode(String barcode) throws ApiException{
        return productDao.selectPojoByBarcode(barcode);
    }
    @Transactional
    public ProductPojo getPojoFromId(Integer id) throws ApiException{
        return productDao.selectPojoById(id);
    }
//
    @Transactional(rollbackOn  = ApiException.class)
    public void update(ProductPojo productPojo) throws ApiException {
      productDao.update(productPojo);
    }
      public ProductPojo givePojoById(Integer id){
            return productDao.selectPojoById(id);
      }
        public ProductPojo givePojoByBarcode(String barcode){
            return productDao.selectPojoByBarcode(barcode);
        }
        public ProductPojo getCheckByBarcode(String barcode) throws ApiException{
            ProductPojo p = productDao.selectPojoByBarcode(barcode);
            if(p == null){
                throw new ApiException("The product with given barcode does not exist");
            }
            return p;
        }
//
//
}
