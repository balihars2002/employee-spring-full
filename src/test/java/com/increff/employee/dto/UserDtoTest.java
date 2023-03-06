package com.increff.employee.dto;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.api.ApiException;
import com.increff.employee.model.data.UserData;
import com.increff.employee.model.form.LoginForm;
import com.increff.employee.model.form.UserForm;
import com.increff.employee.pojo.UserPojo;
import io.swagger.annotations.Api;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDtoTest extends AbstractUnitTest {

    @Autowired
    private UserDto userDto;



    @Test
    public void addTest() throws ApiException {
        UserForm userForm = new UserForm();
        userForm.setRole("operator");
        userForm.setEmail("email");
        userForm.setPassword("password");
        userDto.add(userForm);
        List<UserData> userDataList = userDto.getAll();
        assertEquals(1,userDataList.size());
    }

    @Test(expected = ApiException.class)
    public void loginTest() throws ApiException{
        MockHttpServletRequest request = new MockHttpServletRequest();
        LoginForm loginForm = new LoginForm();
        loginForm.setEmail("email");
        loginForm.setPassword("password");
        userDto.login(request,loginForm);
    }
    @Test
    public void addTest1() throws ApiException {
        UserForm userForm = new UserForm();
        userForm.setEmail("email");
        userForm.setPassword("password");
        userDto.add(userForm);
        List<UserData> userDataList = userDto.getAll();
        assertEquals(1,userDataList.size());
    }


    @Test(expected = ApiException.class)
    public void addTest2() throws ApiException {
        UserForm userForm = new UserForm();
        userForm.setEmail("email");
        userForm.setPassword("password");
        userDto.add(userForm);
        UserForm userForm1 = new UserForm();
        userForm1.setEmail("email");
        userForm1.setPassword("password");
        userDto.add(userForm1);
    }

    @Test
    public void updateTest() throws ApiException {
        UserForm userForm = new UserForm();
        userForm.setEmail("email");
        userForm.setPassword("password");
        userDto.add(userForm);
        List<UserData> userPojoList = userDto.getAll();
        Integer id = userPojoList.get(0).getId();
        userDto.update(id);
    }

    @Test
    public void getByEmailTest() throws ApiException{
        UserForm userForm = new UserForm();
        userForm.setEmail("email");
        userForm.setPassword("password");
        userDto.add(userForm);
        UserPojo userPojo = userDto.get("email");
        assertEquals("password",userPojo.getPassword());;
    }
    @Test
    public void getAllTest() throws ApiException {
        UserForm userForm = new UserForm();
        userForm.setRole("operator");
        userForm.setEmail("email");
        userForm.setPassword("password");
        userDto.add(userForm);
        List<UserData> userDataList = userDto.getAll();
        assertEquals(1,userDataList.size());
    }

    @Test
    public void convertTest() throws ApiException {
        UserForm userForm = new UserForm();
        userForm.setRole("operator");
        userForm.setEmail("email");
        userForm.setPassword("password");
        userDto.add(userForm);
        UserPojo userPojo = userDto.get("email");
        Authentication authentication = userDto.convertPojoToAuthentication(userPojo);
        boolean d = authentication.isAuthenticated();
        assertEquals((boolean) true,d);
    }

}
