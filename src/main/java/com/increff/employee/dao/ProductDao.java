package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.increff.employee.pojo.ProductPojo;
import org.springframework.stereotype.Repository;
@Repository
public class ProductDao extends AbstractDao {

    private static  String DELETE_ID = "delete from ProductPojo where id=:id";
    private static  String DELETE_BARCODE = "delete from ProductPojo where barcode=:barcode";
    private static String SELECT_ID = "select p from ProductPojo p where id=:id";
    private static  String SELECT_BARCODE = "select p from ProductPojo p where barcode=:barcode";
    private static String SELECT_ALL = "select p from ProductPojo p";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(ProductPojo p) {
        em.persist(p);
    }

    public int delete(int id) {
        Query query = em.createQuery(DELETE_ID);
        query.setParameter("id", id);
        return query.executeUpdate();
    }
    public int delete(String barcode) {
        Query query = em.createQuery(DELETE_BARCODE);
        query.setParameter("barcode", barcode);
        return query.executeUpdate();
    }
    public ProductPojo selectPojoById(int id) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_ID, ProductPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public ProductPojo selectPojoByBarcode(String barcode){
        TypedQuery<ProductPojo> query = getQuery(SELECT_BARCODE, ProductPojo.class);
        query.setParameter("barcode", barcode);
        return getSingle(query);
    }
    //getting the id from brand and category
//    public ProductPojo select(String brand,String category){
//        TypedQuery<ProductPojo> query = getQuery(get_brandcategory_id, ProductPojo.class);
//        query.setParameter("brand",brand);
//        query.setParameter("category",category);
//        return getSingle(query);
//    }
    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(SELECT_ALL, ProductPojo.class);
        return query.getResultList();
    }

    public void update(ProductPojo p) {

    }


}
