package com.increff.employee.dao;

import java.util.List;

import javax.persistence.TypedQuery;


import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.UserPojo;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDao extends AbstractDao {

	private final static String GET_BY_ID = "select p from UserPojo p where id=:id";
	private final static String GET_BY_EMAIL = "select p from UserPojo p where email=:email";
	private final static String GET_ALL = "select p from UserPojo p";

	
	@Transactional
	public void insert(UserPojo p) {
		em().persist(p);
	}

	public UserPojo getById(int id) {
		TypedQuery<UserPojo> query = getQuery(GET_BY_ID, UserPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public UserPojo getByEmail(String email) {
		TypedQuery<UserPojo> query = getQuery(GET_BY_EMAIL, UserPojo.class);
		query.setParameter("email", email);
		return getSingle(query);
	}

	public List<UserPojo> getAll() {
		TypedQuery<UserPojo> query = getQuery(GET_ALL, UserPojo.class);
		return query.getResultList();
	}

	public void update(UserPojo p) {
	}


}
