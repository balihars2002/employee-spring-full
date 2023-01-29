package com.increff.employee.dao;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.OrderPojo;
import javassist.bytecode.stackmap.TypedBlock;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderDao extends AbstractDao {

    private static final String SELECT_ALL = "select p from OrderPojo p";

    private static final String DELETE_ID = "delete from OrderPojo p where id=:id";

    private static final String REPORT_FULL = "select p from OrderPojo p where DATE(dateTime)>=DATE(:startDate) and DATE(dateTime)<=DATE(:endDate)";

  //  private static final String REPORT_CATEGORY = "select p from OrderPojo p where DATE(dateTime)>=DATE(:startDate) and DATE(dateTime)<=DATE(:endDate) and category=:category";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderPojo orderPojo){
        em.persist(orderPojo);
    }

    public Integer delete(Integer id) {
        Query query = em.createQuery(DELETE_ID);
        query.setParameter("id", id);
        return query.executeUpdate();
    }


    public List<OrderPojo> selectAll(){
        TypedQuery<OrderPojo> query = getQuery(SELECT_ALL,OrderPojo.class);
        return query.getResultList();
    }


    public void update(OrderPojo p) {
    }

    public List<OrderPojo> selectInDate(LocalDate startDate, LocalDate endDate){
        TypedQuery<OrderPojo> query = getQuery(REPORT_FULL,OrderPojo.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

}




















