package com.increff.employee.dao;

import com.increff.employee.pojo.DailySalesPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.List;

@Repository
public class DailySalesDao extends AbstractDao{

    private static final String SELECT_ALL = "select p from DailySalesPojo p";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(DailySalesPojo pojo){
        em.persist(pojo);
    }

    public List<DailySalesPojo> getAll(){
        TypedQuery<DailySalesPojo> query = getQuery(SELECT_ALL, DailySalesPojo.class);
        return query.getResultList();
    }

}
