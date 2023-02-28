package com.increff.employee.api;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.pojo.UserPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserApiTest extends AbstractUnitTest {

    @Autowired
    private UserApi userApi;


    @Test
    public void addTest() throws ApiException {
        UserPojo userPojo = createPojo();
        userApi.add(userPojo);
        List<UserPojo> userPojoList = userApi.getAll();
        assertEquals(1,userPojoList.size());
    }

    @Test
    public void getTest() throws ApiException {
        UserPojo userPojo = createPojo();
        userApi.add(userPojo);
        List<UserPojo> userPojoList = userApi.getAll();
        UserPojo userPojo1 = userApi.get(userPojoList.get(0).getEmail());
        assertEquals("operator",userPojo1.getRole());
        assertEquals("email",userPojo1.getEmail());
        assertEquals("password",userPojo1.getPassword());
    }


    @Test
    public void getAllTest() throws ApiException {
        UserPojo userPojo = createPojo();
        userApi.add(userPojo);
        List<UserPojo> userPojoList = userApi.getAll();
        assertEquals(1,userPojoList.size());
    }

    public UserPojo createPojo(){
        UserPojo userPojo = new UserPojo();
        userPojo.setEmail("email");
        userPojo.setRole("operator");
        userPojo.setPassword("password");
        return userPojo;
    }
}
