package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.stereotype.Repository;
@Repository
public class ProductDao extends AbstractDao {

    private static  String delete_id = "delete from ProductPojo where id=:id";
    private static  String delete_barcode = "delete from ProductPojo where barcode=:barcode";
    private static String  select_id= "select p from ProductPojo where id= :id";
    private static  String select_barcode= "select p from ProductPojo where barcode= :barcode";
    private static String select_all= "select p from ProductPojo p";
   // private static String get_brandcategory_id = "select id from BrandPojo where brand=:brand and category=:category";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(ProductPojo p) {
        em.persist(p);
    }

    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }
    public int delete(String barcode) {
        Query query = em.createQuery(delete_barcode);
        query.setParameter("barcode", barcode);
        return query.executeUpdate();
    }
    public ProductPojo select(int id) {
        TypedQuery<ProductPojo> query = getQuery(select_id, ProductPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public ProductPojo select(String barcode){
        TypedQuery<ProductPojo> query = getQuery(select_barcode, ProductPojo.class);
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
        TypedQuery<ProductPojo> query = getQuery(select_all, ProductPojo.class);
        return query.getResultList();
    }

    public void update(ProductPojo p) {

    }




}
