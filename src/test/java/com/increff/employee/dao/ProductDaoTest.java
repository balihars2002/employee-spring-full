package com.increff.employee.dao;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.dto.BrandDto;
import com.increff.employee.dto.ProductDto;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class ProductDaoTest extends AbstractUnitTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private BrandDao brandDao;

//    @Before
//    public void beforeTest(){
//        Integer id = addBrand("brand","category",false);
//        addProduct("barcode","",(Double)10.0,(Integer) id);
//    }
//

    @Test
    public void insertTest(){
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double)10.0,(Integer) id);
        List<ProductPojo> productPojoList = productDao.selectAll();
        assertEquals("barcode",productPojoList.get(0).getBarcode());
        assertEquals("name",productPojoList.get(0).getName());
        assertEquals((Double)10.0,productPojoList.get(0).getMrp());
        assertEquals((Integer) id,productPojoList.get(0).getBrand_category());
    }

    @Test
    public void insertTest1(){
        Integer id = addBrand("brand","category",false);
        addProduct("","name",(Double)10.0,(Integer) id);
        List<ProductPojo> productPojoList = productDao.selectAll();
        assertEquals("",productPojoList.get(0).getBarcode());
        assertEquals("name",productPojoList.get(0).getName());
        assertEquals((Double)10.0,productPojoList.get(0).getMrp());
        assertEquals((Integer) id,productPojoList.get(0).getBrand_category());
    }

    @Test
    public void insertTest2(){
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","",(Double)10.0,(Integer) id);
        List<ProductPojo> productPojoList = productDao.selectAll();
        assertEquals("barcode",productPojoList.get(0).getBarcode());
        assertEquals("",productPojoList.get(0).getName());
        assertEquals((Double)10.0,productPojoList.get(0).getMrp());
        assertEquals((Integer) id,productPojoList.get(0).getBrand_category());
    }

    @Test
    public void deleteTest(){
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double)10.0,(Integer) id);
        List<ProductPojo> productPojoList = productDao.selectAll();
        productDao.delete(productPojoList.get(0).getId());
        List<ProductPojo> productPojoList1 = productDao.selectAll();
        assertEquals(Optional.of((Integer) 0), productPojoList1.size());
    }

    @Test
    public void deleteTest1(){
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double)10.0,(Integer) id);
        List<ProductPojo> productPojoList = productDao.selectAll();
        productDao.delete(productPojoList.get(0).getBarcode());
        List<ProductPojo> productPojoList1 = productDao.selectAll();
        assertEquals(0, productPojoList1.size());
    }

    @Test
    public void deleteTest2(){
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double)10.0,(Integer) id);
        List<ProductPojo> productPojoList = productDao.selectAll();
        productDao.delete("bar");
        List<ProductPojo> productPojoList1 = productDao.selectAll();
        assertEquals(1, productPojoList1.size());
    }

    @Test
    public void selectPojoByIdTest(){
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double)10.0,(Integer) id);
        List<ProductPojo> productPojoList = productDao.selectAll();
        ProductPojo productPojo = productPojoList.get(0);
        ProductPojo productPojo1 = productDao.selectPojoById(productPojo.getId());
        assertEquals("name",productPojo1.getName());
        assertEquals("barcode",productPojo1.getBarcode());
        assertEquals("brand",brandDao.select(productPojo1.getBrand_category()).getBrand());
        assertEquals("category",brandDao.select(productPojo1.getBrand_category()).getCategory());
        assertEquals((Double) 10.0,productPojo1.getMrp());
    }

    @Test
    public void selectPojoByIdTest1(){
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double)10.0,(Integer) id);
        List<ProductPojo> productPojoList = productDao.selectAll();
        ProductPojo productPojo = productPojoList.get(0);
        ProductPojo productPojo1 = productDao.selectPojoById(productPojo.getId());
        assertEquals("name",productPojo1.getName());
        assertEquals("barcode",productPojo1.getBarcode());
        assertEquals("brand",brandDao.select(productPojo1.getBrand_category()).getBrand());
        assertEquals("category",brandDao.select(productPojo1.getBrand_category()).getCategory());
        assertEquals((Double) 10.0,productPojo1.getMrp());
    }

    @Test
    public void selectPojoByBarcodeTest(){
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double)10.0,(Integer) id);
        List<ProductPojo> productPojoList = productDao.selectAll();
        ProductPojo productPojo = productPojoList.get(0);
        ProductPojo productPojo1 = productDao.selectPojoByBarcode(productPojo.getBarcode());
        assertEquals("name",productPojo1.getName());
        assertEquals("barcode",productPojo1.getBarcode());
        assertEquals("brand",brandDao.select(productPojo1.getBrand_category()).getBrand());
        assertEquals("category",brandDao.select(productPojo1.getBrand_category()).getCategory());
        assertEquals((Double) 10.0,productPojo1.getMrp());
    }

    @Test
    public  void selectAllTest(){
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double)10.0,(Integer) id);
        id = addBrand("brand1","category1",false);
        addProduct("barcode1","name1",(Double)20.0,(Integer) id);
        List<ProductPojo> productPojoList = productDao.selectAll();
        assertEquals(2,productPojoList.size());
    }

    @Test
    public  void selectAllTest1(){
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double)10.0,(Integer) id);
        //id = addBrand("brand","category",false);
        addProduct("barcode1","name1",(Double)20.0,(Integer) id);
        List<ProductPojo> productPojoList = productDao.selectAll();
        assertEquals(2,productPojoList.size());
    }

//    @Test
//    public  void selectAllTest2(){
//        Integer id = addBrand("brand","category",false);
//        addProduct("barcode","name",(Double)10.0,(Integer) id);
//        //id = addBrand("brand","category",false);
//        addProduct("barcode","name",(Double)10.0,(Integer) id);
//        List<ProductPojo> productPojoList = productDao.selectAll();
//        assertEquals(1,productPojoList.size());
//    }


    @Test
    public void updateTest() {
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double)10.0,(Integer) id);
        List<ProductPojo> productPojoList = productDao.selectAll();
        ProductPojo productPojo = productPojoList.get(0);
        productPojo.setBarcode("updatedBarcode");
        productDao.update(productPojo);
        productPojoList = productDao.selectAll();
        assertEquals("updatedBarcode",productPojoList.get(0).getBarcode());
    }

    @Test
    public void updateTest1() {
        Integer id = addBrand("brand","category",false);
        addProduct("barcode","name",(Double)10.0,(Integer) id);
        List<ProductPojo> productPojoList = productDao.selectAll();
        ProductPojo productPojo = productPojoList.get(0);
        productPojo.setName("updatedName");
        productDao.update(productPojo);
        productPojoList = productDao.selectAll();
        assertEquals("updatedName",productPojoList.get(0).getName());
    }

    public void addProduct(String barcode,String name,Double mrp,Integer id){
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setBarcode(barcode);
        productPojo.setMrp(mrp);
        productPojo.setBrand_category(id);
        productDao.insert(productPojo);
    }
    public Integer addBrand(String brand,String category,Boolean isDisabled){
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brand);
        brandPojo.setCategory(category);
        brandPojo.setDisabled(isDisabled);
        brandDao.insert(brandPojo);
        List<BrandPojo> brandPojoList = brandDao.selectAll();
        return brandPojoList.get(0).getId();
    }
}
