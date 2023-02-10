package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


import org.springframework.stereotype.Repository;
import com.increff.employee.pojo.BrandPojo;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BrandDao extends AbstractDao{
    private final static String DELETE_ID = "delete from BrandPojo p where id=:id";
    private final static String SELECT_ID = "select p from BrandPojo p where id=:id";
    private final static String SELECT_ALL = "select p from BrandPojo p";
    private final static String DUPLICATE_CHECK = "select p from BrandPojo p where brand=:brand and category=:category";
    private final static String POJO_FROM_ID = "select p from BrandPojo p where id=:id";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void
    insert(BrandPojo p) {
        em.persist(p);
    }

    public int delete(Integer id) {
        Query query = em.createQuery(DELETE_ID);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public BrandPojo select(Integer id) {
        TypedQuery<BrandPojo> query = getQuery(SELECT_ID, BrandPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<BrandPojo> selectAll() {
        TypedQuery<BrandPojo> query = getQuery(SELECT_ALL, BrandPojo.class);
        return query.getResultList();
    }

    public BrandPojo selectPojoToCheckDuplicate(String brand, String category){
        TypedQuery<BrandPojo> query = getQuery(DUPLICATE_CHECK, BrandPojo.class);
        query.setParameter("brand",brand);
        query.setParameter("category",category);
        return getSingle(query);
    }

    public void update(BrandPojo p) {
    }



}
