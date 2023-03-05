package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


import com.increff.employee.pojo.ProductPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductDao extends AbstractDao {

    private static final String SELECT_ID = "select p from ProductPojo p where id=:id";
    private static final String SELECT_BARCODE = "select p from ProductPojo p where barcode=:barcode";
    private static final String SELECT_ALL = "select p from ProductPojo p";
    private static final String SELECT_PRODUCT_FROM_NAME_BRAND = "select p from ProductPojo p where name=:name and brand_category=:brand_category";
    private static final String SELECT_PRODUCT_FROM_BRAND = "select p from ProductPojo p where brand_category=:brand_category";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(ProductPojo p) {
        em.persist(p);
    }

//    public Integer delete(Integer id) {
//        Query query = em.createQuery(DELETE_ID);
//        query.setParameter("id", id);
//        return query.executeUpdate();
//    }
//    public Integer delete(String barcode) {
//        Query query = em.createQuery(DELETE_BARCODE);
//        query.setParameter("barcode", barcode);
//        return query.executeUpdate();
//    }
    public ProductPojo getById(Integer id) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_ID, ProductPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public ProductPojo getByBarcode(String barcode){
        TypedQuery<ProductPojo> query = getQuery(SELECT_BARCODE, ProductPojo.class);
        query.setParameter("barcode", barcode);
        return getSingle(query);
    }

    public List<ProductPojo> getByBrandId(Integer brand_category){
        TypedQuery<ProductPojo> query = getQuery(SELECT_PRODUCT_FROM_BRAND, ProductPojo.class);
        query.setParameter("brand_category", brand_category);
        return query.getResultList();
    }

    public List<ProductPojo> getAll() {
        TypedQuery<ProductPojo> query = getQuery(SELECT_ALL, ProductPojo.class);
        return query.getResultList();
    }

    public ProductPojo getCheck(Integer brand_category,String name){
        TypedQuery<ProductPojo> query = getQuery(SELECT_PRODUCT_FROM_NAME_BRAND, ProductPojo.class);
        query.setParameter("brand_category", brand_category);
        query.setParameter("name",name);
        return getSingle(query);
    }

    public void update(ProductPojo p) {

    }


}
