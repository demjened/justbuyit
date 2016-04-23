package com.justbuyit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.entity.Company;
import com.justbuyit.entity.User;

@Controller
@RequestMapping("/")
public class MainController {

    private final static Logger LOG = LoggerFactory.getLogger(MainController.class);
    
    @Autowired
    private CompanyDAO companyDAO;
    
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main(HttpServletRequest req, HttpServletResponse resp) {
        String openId = req.getParameter("openid_url") != null ? req.getParameter("openid_url") : req.getParameter("openid.identity");
        ModelAndView modelAndView = new ModelAndView();
        if (!StringUtils.isEmpty(openId)) {

            // fetch user by openId and proceed
            User user = userDAO.findByOpenId(openId);
            if (user != null && user.isAuthenticated()) {
                modelAndView.addObject("name", user.getFirstName());
                modelAndView.setViewName("main");
                return modelAndView;
            }
        }
        
        // forward to forbidden page
        resp.setStatus(403);
        modelAndView.addObject("message", "Please log in at AppDirect and launch the application from MyApps.");
        modelAndView.setViewName("forbidden");
        return modelAndView;
    }
    
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ModelAndView hello(ModelMap model) {
        LOG.info("/status");
        
        List<Company> companies = companyDAO.findAll();
        model.addAttribute("companies", companies);
        
        return new ModelAndView("status", model);
    }
    
}
