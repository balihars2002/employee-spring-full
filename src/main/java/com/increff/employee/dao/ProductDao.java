//package com.increff.employee.dao;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import javax.persistence.TypedQuery;
//import javax.transaction.Transactional;
//
//import com.increff.employee.pojo.ProductPojo;
//import org.springframework.stereotype.Repository;
//public class ProductDao extends AbstractDao {
//
//    private static  String delete_id = "delete from ProductPojo where id=:id";
//    private static String  select_id= "select p from ProductPojo where id= :id";
//    private static String select_all= "select p from ProductPojo p";
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Transactional
//    public void insert(ProductPojo p) {
//        em.persist(p);
//    }
//    public  int delete(int id){
//
//    }
//
//}
