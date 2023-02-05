package com.increff.employee.dao;

import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.SchedulerPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.List;

@Repository
public class SchedulerDao extends AbstractDao{

    private static final String SELECT_ALL = "select p from SchedulerPojo p";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(SchedulerPojo pojo){
        em.persist(pojo);
    }

    public List<SchedulerPojo> selectAll(){
        TypedQuery<SchedulerPojo> query = getQuery(SELECT_ALL,SchedulerPojo.class);
        return query.getResultList();
    }

}
