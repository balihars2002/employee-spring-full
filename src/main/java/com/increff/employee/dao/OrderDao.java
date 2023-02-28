package com.increff.employee.dao;

import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.api.ApiException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Repository
public class OrderDao extends AbstractDao {

    private static final String SELECT_ALL = "select p from OrderPojo p";
    private static final String DELETE_BY_ID = "delete from OrderPojo p where id=:id";
    private static final String REPORT_FULL = "select p from OrderPojo p where DATE(orderLocalTime)>=DATE(:startDate) and DATE(orderLocalTime)<=DATE(:endDate)";
    private static final String PER_DAY_ORDERS = "select p from OrderPojo p where DATE(orderLocalTime)=DATE(:date)";
    private static final String SELECT_BY_ID = "select p from OrderPojo p where id=:id";


    @PersistenceContext
    private EntityManager em;

    @Transactional(rollbackFor = ApiException.class)
    public void insert(OrderPojo orderPojo){
        em.persist(orderPojo);
    }

    @Transactional(rollbackFor = ApiException.class)
    public Integer delete(Integer id) {
        Query query = em.createQuery(DELETE_BY_ID);
        query.setParameter("id", id);
        return query.executeUpdate();
    }
    @Transactional
    public List<OrderPojo> selectAll(){
        TypedQuery<OrderPojo> query = getQuery(SELECT_ALL,OrderPojo.class);
        return query.getResultList();
    }

    @Transactional
    public List<OrderPojo> selectInDate(LocalDate startDate, LocalDate endDate){
        TypedQuery<OrderPojo> query = getQuery(REPORT_FULL,OrderPojo.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    @Transactional
    public OrderPojo selectBYId(Integer id){
        TypedQuery<OrderPojo> query = getQuery(SELECT_BY_ID,OrderPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    @Transactional
    public List<OrderPojo> getOrdersForScheduler(LocalDate date){
        TypedQuery<OrderPojo> query = getQuery(PER_DAY_ORDERS,OrderPojo.class);
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(OrderPojo pojo){

    }

}




















