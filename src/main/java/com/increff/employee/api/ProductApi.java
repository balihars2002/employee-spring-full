package com.increff.employee.api;


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
    public void insert(ProductPojo productPojo) throws ApiException {
          productDao.insert(productPojo);
    }

    @Transactional
    public List<ProductPojo> getAll() {
        return productDao.getAll();
    }

    @Transactional
    public ProductPojo getByBarcode(String barcode) throws ApiException{
        ProductPojo productPojo = productDao.getByBarcode(barcode);
        if(productPojo == null){
            throw new ApiException("Product with given barcode does not exist.");
        }
        return productPojo;
    }


    @Transactional
    public ProductPojo getPojoFromId(Integer id) throws ApiException{
        ProductPojo productPojo1 = productDao.getById(id);
        if(productPojo1 == null)
        {
            throw new ApiException("The product with given id does not exist.");
        }
        return productPojo1;
    }

    @Transactional
    public List<ProductPojo> getByBrandId(Integer brandId) throws ApiException{
        return productDao.getByBrandId(brandId);
    }



    @Transactional(rollbackFor  = ApiException.class)
    public void update(ProductPojo productPojo) throws ApiException {
        ProductPojo productPojo1 = getById(productPojo.getId());
        productPojo1.setName(productPojo.getName());
        productPojo1.setBarcode(productPojo.getBarcode());
        productPojo1.setMrp(productPojo.getMrp());
        productDao.update(productPojo1);
    }

    @Transactional
    public ProductPojo getById(Integer id) throws ApiException{
        ProductPojo productPojo = productDao.getById(id);
        if(productPojo == null){
            throw new ApiException("Product with given ProductId does not exist.");
        }
            return productPojo;
      }


    @Transactional
    public void getCheck(Integer brand_category,String name) throws ApiException {
        ProductPojo p = productDao.getCheck(brand_category,name);
        if(p != null){
            throw new ApiException("The product with same brand,category and name already exists");
        }
    }
//

    @Transactional
    public void getPojoByBarcode(String barcode) throws ApiException{
        ProductPojo productPojo = productDao.getByBarcode(barcode);
        if(productPojo != null) {
            throw new ApiException("Product with given barcode already exist.");
        }
    }

//
}
