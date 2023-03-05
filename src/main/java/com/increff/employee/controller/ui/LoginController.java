package com.increff.employee.controller.ui;

import com.increff.employee.api.ApiException;
import com.increff.employee.dto.UserDto;
import com.increff.employee.model.data.InfoData;
import com.increff.employee.model.form.LoginForm;
import com.increff.employee.pojo.UserPojo;
import com.increff.employee.util.SecurityUtil;
import com.increff.employee.util.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Objects;

@Api
@RestController
public class LoginController {

    @Autowired
    private UserDto userDto;
    @Autowired
    private InfoData info;

    @ApiOperation(value = "Logs in a user")
    @RequestMapping(path = "/session/login", method = RequestMethod.POST)
    public void login(HttpServletRequest req, @RequestBody LoginForm f) throws ApiException, NullPointerException {
        UserPojo p = userDto.get(f.getEmail());
        boolean authenticated = (p != null && Objects.equals(p.getPassword(), f.getPassword()));
        if (!authenticated) {
            throw new ApiException("Invalid username or password");
        }
        if (p.getDisabled()) {
            throw new ApiException("User is Restricted! Contact Supervisor");
        } else {
            // Create authentication object
            Authentication authentication = userDto.convertPojoToAuthentication(p);
            // Create new session
            HttpSession session = req.getSession(true);
            // Attach Spring SecurityContext to this new session
            SecurityUtil.createContext(session);
            // Attach Authentication object to the Security Context
            SecurityUtil.setAuthentication(authentication);
//			return new ModelAndView("redirect:/ui/order");
        }

    }
    @RequestMapping(path = "/session/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return new ModelAndView("redirect:/site/logout");
    }


}
