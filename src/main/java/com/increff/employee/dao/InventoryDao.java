package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.increff.employee.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryDao extends AbstractDao {


    private static String DELETE_BY_ID = "delete from InventoryPojo p where id=:id";
    private static String SELECT_BY_ID ="select p from InventoryPojo p where id=:id";
    private static String SELECT_BY_BARCODE ="select p from InventoryPojo p where barcode=:barcode";
    private static String SELECT_ALL = "select p from InventoryPojo p";
    //private static String update_by_id= "UPDATE InventoryPojo Set quantity=:quantity WHERE id=:id";

    //private static String update_by_id = "update InventoryPojo where";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(InventoryPojo p) {
        em.persist(p);
    }

    public List<InventoryPojo> selectAlls() {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_ALL, InventoryPojo.class);
        return query.getResultList();
    }
    public int delete(int id) {
        Query query = em.createQuery(DELETE_BY_ID);
        query.setParameter("id", id);
        return query.executeUpdate();
    }
//    public ProductPojo selectpojobyid(int id) {
//        TypedQuery<ProductPojo> query = getQuery(select_id, ProductPojo.class);
//        query.setParameter("id", id);
//        return getSingle(query);
//    }
    public InventoryPojo selectPojoByBarcode(String barcode){
        TypedQuery<InventoryPojo> query= getQuery(SELECT_BY_BARCODE, InventoryPojo.class);
        query.setParameter("barcode",barcode);
        return getSingle(query);
    }
    public InventoryPojo selectPojoById(int id){
        TypedQuery<InventoryPojo> query= getQuery(SELECT_BY_ID, InventoryPojo.class);
        query.setParameter("id",id);
        return getSingle(query);
    }
//        public void updateInventory(int id){
//            Query query = em.createQuery(update_by_id);
//            query.setParameter("id",id);
//            return getSingle(query);
//        }
        public void update(InventoryPojo inventoryPojo) {

        }
}
