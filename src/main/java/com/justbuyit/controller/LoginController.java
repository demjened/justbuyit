package com.justbuyit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.discovery.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.justbuyit.auth.OpenIdAuthorizer;
import com.justbuyit.dao.UserDAO;

@Controller
@RequestMapping
public class LoginController {
    
    private final static Logger LOG = LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    private OpenIdAuthorizer openIdAuthorizer;
    
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String openId = req.getParameter("openid_url");
        
        LOG.info("/login :: {}", openId);
        
        if (userDAO.isAuthenticated(openId)) {
            LOG.debug("User with openId [{}] is already authenticated, forwarding to /main");
            return "forward:/main";
        } else {
            LOG.debug("User with openId [{}] has not been authenticated yet, redirecting to /openid");
            openIdAuthorizer.authRequest(openId, req.getRequestURL().append("/openid").toString(), req, resp);
            return null;
        }
    }
    
    @RequestMapping(value = "/login/openid", method = RequestMethod.GET)
    public String openid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        LOG.info("/login/openid");

        Identifier identity = openIdAuthorizer.verifyResponse(req);
        if (identity != null) {
            // set authenticated
            userDAO.setAuthenticated(identity.getIdentifier(), true);
        }
        
        return "forward:/main";
    }
    
}
