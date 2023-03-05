package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


import com.increff.employee.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class InventoryDao extends AbstractDao {


    private static final String SELECT_BY_ID ="select p from InventoryPojo p where id=:id";
    private static final String SELECT_BY_BARCODE ="select p from InventoryPojo p where barcode=:barcode";
    private static final String SELECT_ALL = "select p from InventoryPojo p";
    private static final String SELECT_BY_PID ="select p from InventoryPojo p where productId=:id";
    private static final String INVENTORY_REPORT ="select p from InventoryPojo p where brand=:id or :pro";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(InventoryPojo inventoryPojo) {
        em.persist(inventoryPojo);
    }
    public List<InventoryPojo> getAll() {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_ALL, InventoryPojo.class);
        return query.getResultList();
    }
//    public Integer delete(Integer id) {
//        Query query = em.createQuery(DELETE_BY_ID);
//        query.setParameter("id", id);
//        return query.executeUpdate();
//    }
    public InventoryPojo getByBarcode(String barcode){
        TypedQuery<InventoryPojo> query= getQuery(SELECT_BY_BARCODE, InventoryPojo.class);
        query.setParameter("barcode",barcode);
        return getSingle(query);
    }
    public InventoryPojo getById(Integer id){
        TypedQuery<InventoryPojo> query= getQuery(SELECT_BY_ID, InventoryPojo.class);
        query.setParameter("id",id);
        return getSingle(query);
    }
    public InventoryPojo getByProductId(Integer id){
        TypedQuery<InventoryPojo> query= getQuery(SELECT_BY_PID, InventoryPojo.class);
        query.setParameter("id",id);
        return getSingle(query);
    }
    public void update(InventoryPojo inventoryPojo) {

    }
}
