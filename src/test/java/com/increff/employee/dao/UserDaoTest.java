package com.increff.employee.dao;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.pojo.UserPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.assertEquals;
import java.util.List;

public class UserDaoTest extends AbstractUnitTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void addTest(){
        UserPojo userPojo = createPojo();
        userDao.insert(userPojo);
        List<UserPojo> userPojoList = userDao.getAll();
        assertEquals(1,userPojoList.size());
    }



    @Test
    public void selectTest(){
        UserPojo userPojo = createPojo();
        userDao.insert(userPojo);
        List<UserPojo> userPojoList = userDao.getAll();
        UserPojo userPojo1 = userDao.getById(userPojoList.get(0).getId());
        assertEquals("operator",userPojo1.getRole());
        assertEquals("email",userPojo1.getEmail());
        assertEquals("password",userPojo1.getPassword());
    }

    @Test
    public void updateTest(){
        UserPojo userPojo = new UserPojo();
        userDao.update(userPojo);
    }

    @Test
    public void selectTest1(){
        UserPojo userPojo = createPojo();
        userDao.insert(userPojo);
        List<UserPojo> userPojoList = userDao.getAll();
        UserPojo userPojo1 = userDao.getByEmail(userPojoList.get(0).getEmail());
        assertEquals("operator",userPojo1.getRole());
        assertEquals("email",userPojo1.getEmail());
        assertEquals("password",userPojo1.getPassword());
    }


    public UserPojo createPojo(){
        UserPojo userPojo = new UserPojo();
        userPojo.setEmail("email");
        userPojo.setRole("operator");
        userPojo.setPassword("password");
        return userPojo;
    }
}
