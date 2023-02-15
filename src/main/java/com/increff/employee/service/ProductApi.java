package com.increff.employee.service;


import java.util.List;



import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductApi {
    @Autowired
    private ProductDao productDao;

    @Transactional(rollbackFor = ApiException.class)
    public void insertService(ProductPojo productPojo) throws ApiException {
          productDao.insert(productPojo);
    }

    @Transactional
    public void deleteServiceById(Integer id) {
        productDao.delete(id);
    }

    @Transactional
    public List<ProductPojo> selectAllService() {
        return productDao.selectAll();
    }

    @Transactional
    public ProductPojo getPojoFromBarcode(String barcode) throws ApiException{
        ProductPojo productPojo = productDao.selectPojoByBarcode(barcode);
        if(productPojo == null){
            throw new ApiException("Product with given barcode does not exist.");
        }
        return productPojo;
    }
    @Transactional
    public void getPojoByBarcode(String barcode) throws ApiException{
        ProductPojo productPojo = productDao.selectPojoByBarcode(barcode);
        if(productPojo != null) {
            throw new ApiException("Product with given barcode already exist.");
        }
    }
    @Transactional
    public ProductPojo getPojoFromId(Integer id) throws ApiException{
        ProductPojo productPojo1 = productDao.selectPojoById(id);
        if(productPojo1 == null)
        {
            throw new ApiException("The product with given id does not exist.");
        }
        return productPojo1;
    }

    @Transactional(rollbackFor  = ApiException.class)
    public void update(ProductPojo productPojo) throws ApiException {
        ProductPojo productPojo1 = givePojoById(productPojo.getId());
        productPojo1.setName(productPojo.getName());
        productPojo1.setBarcode(productPojo.getBarcode());
        productPojo1.setMrp(productPojo.getMrp());
        productDao.update(productPojo1);

    }

    @Transactional
    public ProductPojo givePojoById(Integer id) throws ApiException{
        ProductPojo productPojo = productDao.selectPojoById(id);
        if(productPojo == null){
            throw new ApiException("Product with given ProductId does not exist.");
        }
            return productPojo;
      }


    @Transactional
    public ProductPojo getCheckByBarcode(String barcode) throws ApiException {
        ProductPojo p = productDao.selectPojoByBarcode(barcode);
            if(p == null){
                throw new ApiException("The product with given barcode does not exist");
        }
        return p;
    }

    @Transactional
    public void getCheck(Integer brand_category,String name) throws ApiException {
        ProductPojo p = productDao.getCheck(brand_category,name);
        if(p != null){
            throw new ApiException("The product with same brand,category and name already exists");
        }
    }
//
//
}
