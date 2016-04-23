package com.justbuyit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.entity.Company;
import com.justbuyit.entity.User;

/**
 * Controller for accessing the main application screen.
 */
@Controller
@RequestMapping("/")
public class MainController {

    private final static Logger LOG = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private UserDAO userDAO;

    /**
     * Loads the main application screen. If the user is not authenticated, points them to the AppDirect launch page.
     * 
     * @param req
     *            the request
     * @param resp
     *            the response
     * @return the next model-and-view
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main(HttpServletRequest req, HttpServletResponse resp) {
        LOG.info("/");

        // extract OpenID from request
        String openId = req.getParameter("openid_url") != null ? req.getParameter("openid_url") : req.getParameter("openid.identity");
        if (!StringUtils.isEmpty(openId)) {

            // fetch user by OpenID and proceed
            User user = userDAO.findByOpenId(openId);
            if (user != null && user.isAuthenticated()) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("name", user.getFirstName());
                modelAndView.setViewName("main");
                return modelAndView;
            }
        }

        // navigate to forbidden page
        resp.setStatus(403);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "Please log in at AppDirect and launch the application from MyApps.");
        modelAndView.setViewName("forbidden");
        return modelAndView;
    }

    /**
     * Loads the status screen that displays a list of subscribed companies and users.
     * 
     * @return the next model-and-view
     */
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ModelAndView status() {
        LOG.info("/status");

        // fetch all companies and related info
        List<Company> companies = companyDAO.findAll();

        // navigate to status page
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("companies", companies);
        modelAndView.setViewName("status");
        return modelAndView;
    }

}
