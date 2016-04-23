package com.justbuyit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.discovery.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.justbuyit.auth.OpenIdAuthorizer;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.entity.User;

@Controller
@RequestMapping
public class LoginController {
    
    private final static Logger LOG = LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    private OpenIdAuthorizer openIdAuthorizer;
    
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String openId = req.getParameter("openid_url");
        
        LOG.info("/login :: {}", openId);
        
        if (!StringUtils.isEmpty(openId)) {

            // fetch user by openId and check if they are authenticated
            User user = userDAO.findByOpenId(openId);
            if (user != null && user.isAuthenticated()) {
                LOG.debug("User with openId [{}] is already authenticated, forwarding to /main", openId);
                return new ModelAndView("forward:/main");
            } else {
                LOG.debug("User with openId [{}] has not been authenticated yet, redirecting to /openid", openId);
                openIdAuthorizer.authRequest(openId, req.getRequestURL().append("/openid").toString(), req, resp);
                return null;
            }
        }
        
        // forward to forbidden page
        resp.setStatus(403);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "Please log in at AppDirect and launch the application from MyApps.");
        modelAndView.setViewName("forbidden");
        return modelAndView;
    }
    
    @RequestMapping(value = "/login/openid", method = RequestMethod.GET)
    public ModelAndView openid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        LOG.info("/login/openid");

        Identifier identity = openIdAuthorizer.verifyResponse(req);
        if (identity != null) {
            
            // fetch user
            String openId = identity.getIdentifier();
            User user = userDAO.findByOpenId(openId);
            if (user == null) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("message", "You have not been assigned to this application.");
                modelAndView.setViewName("forbidden");
                return modelAndView;
            }
            
            // set and persist authenticated flag
            user.setAuthenticated(true);
            userDAO.update(user);
        }
        
        return new ModelAndView("forward:/main");
    }
    
}
