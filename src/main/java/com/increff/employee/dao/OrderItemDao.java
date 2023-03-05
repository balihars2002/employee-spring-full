package com.increff.employee.dao;

import com.increff.employee.api.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao{
    private static final String SELECT_ALL = "select p from OrderItemPojo p";

    private static final String DELETE_BY_PRODUCT_ID = "delete from OrderItemPojo p where productId=:productId";

    private static final String DELETE_BY_ORDER_ID = "delete from OrderItemPojo p where orderId=:orderId";
    private static final String DELETE_BY_ID = "delete from OrderItemPojo p where id=:id";

    private static final String SELECT_SOME = "select p from OrderItemPojo p where orderId=:id";

    private static final String SELECT_BY_ID = "select p from OrderItemPojo p where id=:id";

    @PersistenceContext
    private EntityManager em;

    @Transactional(rollbackFor = ApiException.class)
    public void insert(OrderItemPojo orderItemPojo){
        em.persist(orderItemPojo);
    }


    @Transactional
    public void deleteByProductId(Integer productId) {
        Query query = em.createQuery(DELETE_BY_PRODUCT_ID);
        query.setParameter("productId", productId);
        query.executeUpdate();
    }

    @Transactional
    public void deleteByOrderId(Integer orderId) {
        Query query = em.createQuery(DELETE_BY_ORDER_ID);
        query.setParameter("orderId", orderId);
        query.executeUpdate();
    }

    @Transactional
    public void deleteById(Integer id) {
        Query query = em.createQuery(DELETE_BY_ID);
        query.setParameter("id", id);
        query.executeUpdate();
    }


    @Transactional
    public List<OrderItemPojo> getAll(){
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_ALL,OrderItemPojo.class);
        return query.getResultList();
    }

    @Transactional
    public List<OrderItemPojo> getByOrderId(Integer id){
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_SOME,OrderItemPojo.class);
        query.setParameter("id",id);
        return query.getResultList();
    }

    @Transactional
    public OrderItemPojo getById(Integer id){
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ID,OrderItemPojo.class);
        query.setParameter("id",id);
        return getSingle(query);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(OrderItemPojo pojo) {
    }



}
