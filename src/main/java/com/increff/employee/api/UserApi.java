package com.increff.employee.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.employee.dao.UserDao;
import com.increff.employee.pojo.UserPojo;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserApi {

	@Autowired
	private UserDao dao;

	@Transactional(rollbackFor = ApiException.class)
	public void add(UserPojo p) throws ApiException {
		UserPojo existing = dao.getByEmail(p.getEmail());
		dao.insert(p);
	}

	@Transactional
	public UserPojo get(String email) throws ApiException {
		return dao.getByEmail(email);
	}

	@Transactional
	public UserPojo getById(Integer id) throws ApiException {
		return dao.getById(id);
	}

	@Transactional
	public List<UserPojo> getAll() {
		return dao.getAll();
	}

	@Transactional(rollbackFor = ApiException.class)
	public void update(Integer id) throws ApiException {
		UserPojo userPojo = getById(id);
		userPojo.setDisabled(true);
		dao.update(userPojo);
	}


}
