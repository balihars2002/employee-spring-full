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


    private static final String DELETE_BY_ID = "delete from InventoryPojo p where id=:id";
    private static final String SELECT_BY_ID ="select p from InventoryPojo p where id=:id";
    private static final String SELECT_BY_BARCODE ="select p from InventoryPojo p where barcode=:barcode";
    private static final String SELECT_ALL = "select p from InventoryPojo p";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(InventoryPojo inventoryPojo) {
        em.persist(inventoryPojo);
    }
    public List<InventoryPojo> selectAlls() {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_ALL, InventoryPojo.class);
        return query.getResultList();
    }
    public Integer delete(Integer id) {
        Query query = em.createQuery(DELETE_BY_ID);
        query.setParameter("id", id);
        return query.executeUpdate();
    }
    public InventoryPojo selectPojoByBarcode(String barcode){
        TypedQuery<InventoryPojo> query= getQuery(SELECT_BY_BARCODE, InventoryPojo.class);
        query.setParameter("barcode",barcode);
        return getSingle(query);
    }
    public InventoryPojo selectPojoById(Integer id){
        TypedQuery<InventoryPojo> query= getQuery(SELECT_BY_ID, InventoryPojo.class);
        query.setParameter("id",id);
        return getSingle(query);
    }
    public void update(InventoryPojo inventoryPojo) {

    }
}
