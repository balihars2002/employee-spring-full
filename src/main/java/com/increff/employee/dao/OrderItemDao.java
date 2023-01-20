package com.increff.employee.dao;

import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import com.increff.employee.pojo.OrderItemPojo;

import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao{
    private static String SELECT_ALL = "select p from OrderItemPojo p";

    private static String DELETE_BY_PRODUCTID = "delete from OrderItemPojo p where productId=:productId";

    private static String DELETE_BY_ORDERID = "delete from OrderItemPojo p where orderId=:orderId";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderItemPojo orderItemPojo){
        em.persist(orderItemPojo);
    }


    public int deleteByProductId(int product_id) {
        Query query = em.createQuery(DELETE_BY_PRODUCTID);
        query.setParameter("product_id", product_id);
        return query.executeUpdate();
    }
    public int deleteByOrderId(int order_id) {
        Query query = em.createQuery(DELETE_BY_ORDERID);
        query.setParameter("order_id", order_id);
        return query.executeUpdate();
    }

    public List<OrderItemPojo> selectAll(){
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_ALL,OrderItemPojo.class);
        return query.getResultList();
    }

    public void update(OrderItemPojo pojo) {
    }



}
