package com.increff.employee.service;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductApiTest extends AbstractUnitTest {

    @Autowired
    private ProductApi productApi;
    @Autowired
    private BrandApi brandApi;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private BrandDao brandDao;
    @Test
    public void addTest() throws ApiException{
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double)10.0,(Integer) id);
        List<ProductPojo> productPojoList = productApi.selectAllService();
        assertEquals("barcode",productPojoList.get(0).getBarcode());
        assertEquals("name",productPojoList.get(0).getName());
        assertEquals((Double)10.0,productPojoList.get(0).getMrp());
        assertEquals((Integer) id,productPojoList.get(0).getBrand_category());
    }

    @Test
    public void deleteTest() throws ApiException{
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double)10.0,(Integer) id);
        List<ProductPojo> productPojoList = productApi.selectAllService();
        productApi.deleteServiceById(productPojoList.get(0).getId());
        assertEquals(0,productApi.selectAllService().size());
    }

    @Test
    public void selectAllTest() throws ApiException {
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double)10.0,(Integer) id);
        addProduct("barcode1","name1",(Double)20.0,(Integer) id);
        List<ProductPojo> productPojoList = productApi.selectAllService();
        assertEquals(2,productPojoList.size());
    }

    @Test
    public void getPojoFromBarcodeTest() throws ApiException{
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double) 10.0,(Integer) id);
        ProductPojo productPojo = productApi.getPojoFromBarcode("barcode");
        assertEquals("barcode",productPojo.getBarcode());
        assertEquals("name",productPojo.getName());
        assertEquals((Double) 10.0,productPojo.getMrp());
        assertEquals(id,productPojo.getBrand_category());
    }
    @Test(expected = ApiException.class)
    public void getPojoByBarcodeTest() throws ApiException{
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double) 10.0,(Integer) id);
        productApi.getPojoByBarcode("barcode");
//        assertEquals("barcode",productPojo.getBarcode());
//        assertEquals("name",productPojo.getName());
//        assertEquals((Double) 10.0,productPojo.getMrp());
//        assertEquals(id,productPojo.getBrand_category());
    }

    @Test(expected = ApiException.class)
    public void getPojoFromBarcodeTest1() throws ApiException{
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double) 10.0,(Integer) id);
        ProductPojo productPojo = productApi.getPojoFromBarcode("barcode1");
    }
    @Test
    public void getPojoFromIdTest() throws ApiException{
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double) 10.0,(Integer) id);
        List<ProductPojo> productPojoList = productApi.selectAllService();
        ProductPojo productPojo = productApi.getPojoFromId(productPojoList.get(0).getId());
        assertEquals("barcode",productPojo.getBarcode());
        assertEquals("name",productPojo.getName());
        assertEquals((Double) 10.0,productPojo.getMrp());
        assertEquals(id,productPojo.getBrand_category());
    }

    @Test(expected = ApiException.class)
    public void getPojoFromIdTest1() throws ApiException{
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double) 10.0,(Integer) id);
        List<ProductPojo> productPojoList = productApi.selectAllService();
        ProductPojo productPojo = productApi.getPojoFromId(productPojoList.get(0).getId()+1);
    }


    @Test
    public void givePojoByIdTest() throws ApiException{
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double) 10.0,(Integer) id);
        List<ProductPojo> productPojoList = productApi.selectAllService();
        ProductPojo productPojo = productApi.givePojoById(productPojoList.get(0).getId());
        assertEquals("barcode",productPojo.getBarcode());
        assertEquals("name",productPojo.getName());
        assertEquals((Double) 10.0,productPojo.getMrp());
        assertEquals(id,productPojo.getBrand_category());
    }

    @Test(expected = ApiException.class)
    public void givePojoByIdTest1() throws ApiException{
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double) 10.0,(Integer) id);
        List<ProductPojo> productPojoList = productApi.selectAllService();
        ProductPojo productPojo = productApi.givePojoById(productPojoList.get(0).getId()+1);
    }

    @Test(expected = ApiException.class)
    public void getCheckTest() throws ApiException {
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double) 10.0,(Integer) id);
        List<ProductPojo> productPojoList = productApi.selectAllService();
        productApi.getCheck(id,"name");
    }

    @Test
    public void getCheckTest1() throws ApiException {
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double) 10.0,(Integer) id);
        List<ProductPojo> productPojoList = productApi.selectAllService();
        productApi.getCheck(id+1,"names");
    }

    @Test
    public void updateTest() throws ApiException{
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double) 10.0,(Integer) id);
        List<ProductPojo> productPojoList = productApi.selectAllService();
        ProductPojo productPojo = productPojoList.get(0);
        productPojo.setName("name1");
        productPojo.setBarcode("barcode1");
        productApi.update(productPojo);
        productPojoList= productApi.selectAllService();
        assertEquals("name1",productPojoList.get(0).getName());
        assertEquals("barcode1",productPojoList.get(0).getBarcode());
    }

    public void addProduct(String barcode,String name,Double mrp,Integer id) throws ApiException {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setBarcode(barcode);
        productPojo.setMrp(mrp);
        productPojo.setBrand_category(id);
        productApi.insertService(productPojo);
    }
    public Integer addBrand(String brand,String category,Boolean isDisabled) throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brand);
        brandPojo.setCategory(category);
        brandPojo.setDisabled(isDisabled);
        brandApi.add(brandPojo);
        List<BrandPojo> brandPojoList = brandDao.selectAll();
        return brandPojoList.get(0).getId();
    }
}
