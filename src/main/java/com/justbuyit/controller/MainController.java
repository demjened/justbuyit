package com.justbuyit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.model.Company;

@Controller
@RequestMapping("/")
public class MainController {

    private final static Logger LOG = LoggerFactory.getLogger(MainController.class);
    
    @Autowired
    private CompanyDAO companyDAO;
    
    @Autowired
    private UserDAO userDAO;
    
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ModelAndView hello(ModelMap model) {
        LOG.info("/status");
        
        List<Company> companies = companyDAO.findAll();
        model.addAttribute("companies", companies);
        
        return new ModelAndView("index", model);
    }
    
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView main(HttpServletRequest req, HttpServletResponse resp) {
        
        System.out.println(req.getParameterMap().keySet());
        
        String openId = req.getParameter("openid_url") != null ? req.getParameter("openid_url") : req.getParameter("openid.identity");
        if (userDAO.isAuthenticated(openId)) {
            // fetch user by openId
            
            ModelMap model = new ModelMap();
            model.addAttribute("name", openId);
            
            return new ModelAndView("hello", model);
        } else {
            return null;
        }
    }

}
