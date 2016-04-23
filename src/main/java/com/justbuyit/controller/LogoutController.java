package com.justbuyit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.justbuyit.dao.UserDAO;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    
    private final static Logger LOG = LoggerFactory.getLogger(LogoutController.class);
    
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(method = RequestMethod.GET)
    public String logout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String openId = req.getParameter("openid_url");
        
        LOG.info("/logout :: {}", openId);
        
        //userDAO.setAuthenticated(openId, false);
        return "/main";
    }
    
}
