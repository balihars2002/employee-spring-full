package com.increff.employee.dao;

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

    private static final String SELECT_SOME = "select p from OrderItemPojo p where orderId=:id";

    private static final String SELECT_BY_ID = "select p from OrderItemPojo p where id=:id";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderItemPojo orderItemPojo){
        em.persist(orderItemPojo);
    }


    public void deleteByProductId(Integer product_id) {
        Query query = em.createQuery(DELETE_BY_PRODUCT_ID);
        query.setParameter("product_id", product_id);
        query.executeUpdate();
    }
    public void deleteByOrderId(Integer order_id) {
        Query query = em.createQuery(DELETE_BY_ORDER_ID);
        query.setParameter("order_id", order_id);
        query.executeUpdate();
    }

    public List<OrderItemPojo> selectAll(){
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_ALL,OrderItemPojo.class);
        return query.getResultList();
    }
    public List<OrderItemPojo> selectSome(Integer id){
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_SOME,OrderItemPojo.class);
        query.setParameter("id",id);
        return query.getResultList();
    }

    public OrderItemPojo getPojoFromId(Integer id){
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ID,OrderItemPojo.class);
        query.setParameter("id",id);
        return getSingle(query);
    }

    public void update(OrderItemPojo pojo) {
    }



}
