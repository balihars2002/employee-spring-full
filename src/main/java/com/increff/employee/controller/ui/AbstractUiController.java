package com.increff.employee.controller.ui;

import com.increff.employee.model.data.InfoData;
import com.increff.employee.util.SecurityUtil;
import com.increff.employee.util.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public abstract class AbstractUiController {

    @Autowired
    private InfoData info;

    @Value("${app.baseUrl}")
    private String baseUrl;

    protected ModelAndView mav(String page) {
        // Get current user
        UserPrincipal principal = SecurityUtil.getPrincipal();

        info.setEmail(principal == null ? "" : principal.getEmail());
        info.setRole(principal == null ? "" : principal.getRole());

        // Set info
        ModelAndView mav = new ModelAndView(page);
        mav.addObject("info", info);
        mav.addObject("baseUrl", baseUrl);
        return mav;
    }

}
