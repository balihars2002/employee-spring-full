package com.increff.employee.dao;

import com.increff.employee.model.OrderItemForm;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import com.increff.employee.pojo.OrderItemPojo;
@Repository
public class OrderItemDao extends AbstractDao{
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderItemPojo orderItemPojo){
        em.persist(orderItemPojo);
    }


}
