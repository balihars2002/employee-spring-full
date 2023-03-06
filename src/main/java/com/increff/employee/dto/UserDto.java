package com.increff.employee.dto;
import com.increff.employee.api.ApiException;
import com.increff.employee.api.UserApi;
import com.increff.employee.model.data.UserData;
import com.increff.employee.model.form.LoginForm;
import com.increff.employee.model.form.UserForm;
import com.increff.employee.pojo.UserPojo;
import com.increff.employee.util.SecurityUtil;
import com.increff.employee.util.StringUtil;
import com.increff.employee.util.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserDto {
    @Autowired
    private UserApi userApi;

    @Value("#{'${app.supervisors}'.split(',')}")
    private List<String> emails;

    public void add(UserForm form) throws ApiException {
        UserPojo p = convert(form);
        if (userApi.get(p.getEmail()) != null) {
            throw new ApiException("User with given email already Exists");
        }
        else {
            boolean flag = false;
            for (String email : emails) {
                if (email.equals(form.getEmail())) {
                    form.setRole("supervisor");
                    p.setRole("supervisor");
                    flag = true;
                }
            }
            if (!flag) {
                form.setRole("operator");
                p.setRole("operator");
            }

            userApi.add(p);
            //throw new ApiException("User added successfully");
        }

    }

    public void login(HttpServletRequest req,LoginForm f) throws ApiException{
        UserPojo p = get(f.getEmail());
        boolean authenticated = (p != null && Objects.equals(p.getPassword(), f.getPassword()));
        if (!authenticated) {
            throw new ApiException("Invalid username or password");
        }
        if (p.getDisabled()) {
            throw new ApiException("User is Restricted! Contact Supervisor");
        } else {
            // Create authentication object
            Authentication authentication = convertPojoToAuthentication(p);
            // Create new session
            HttpSession session = req.getSession(true);
            // Attach Spring SecurityContext to this new session
            SecurityUtil.createContext(session);
            // Attach Authentication object to the Security Context
            SecurityUtil.setAuthentication(authentication);
//			return new ModelAndView("redirect:/ui/order");
        }
    }

    public List<UserData> getAll(){
        List<UserPojo> list = userApi.getAll();
		List<UserData> list2 = new ArrayList<>();
		for (UserPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
    }

    public UserPojo get(String email) throws ApiException {
        return userApi.get(email);
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
//    private static void normalize(UserPojo p) {
//        p.setEmail(p.getEmail().toLowerCase().trim());
//        p.setRole(p.getRole().toLowerCase().trim());
//    }

    public Authentication convertPojoToAuthentication(UserPojo p) {
        // Create principal
        UserPrincipal principal = new UserPrincipal();
        principal.setEmail(p.getEmail());
        principal.setId(p.getId());
        principal.setRole(p.getRole());

        // Create Authorities
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(p.getRole()));
        // you can add more roles if required

        // Create Authentication
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, null, authorities);
        return token;
    }



}
