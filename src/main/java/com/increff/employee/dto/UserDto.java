package com.increff.employee.dto;
import com.increff.employee.api.ApiException;
import com.increff.employee.api.UserApi;
import com.increff.employee.model.data.UserData;
import com.increff.employee.model.form.UserForm;
import com.increff.employee.pojo.UserPojo;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDto {
    @Autowired
    private UserApi userApi;

    public void add(UserForm form) throws ApiException {
        if(StringUtil.isEmpty(form.getRole())){
            form.setRole("operator");
        }
        UserPojo p = convert(form);
        normalize(p);
        userApi.add(p);
    }



    public List<UserData> getAll(){
        List<UserPojo> list = userApi.getAll();
		List<UserData> list2 = new ArrayList<>();
		for (UserPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
    }

    public void update(Integer id) throws ApiException {
        userApi.update(id);
    }
    private static UserData convert(UserPojo p) {
        UserData d = new UserData();
        d.setEmail(p.getEmail());
        d.setRole(p.getRole());
        d.setId(p.getId());
        d.setDisabled(p.getDisabled());
        return d;
    }

    private static UserPojo convert(UserForm f) {
        UserPojo p = new UserPojo();
        p.setEmail(f.getEmail());
        p.setRole(f.getRole());
        p.setPassword(f.getPassword());
        return p;
    }
    private static void normalize(UserPojo p) {
        p.setEmail(p.getEmail().toLowerCase().trim());
        p.setRole(p.getRole().toLowerCase().trim());
    }
}
