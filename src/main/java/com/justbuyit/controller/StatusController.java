package com.justbuyit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.entity.Company;

/**
 * Controller for accessing the status screen.
 */
@Controller
@RequestMapping(value = "/status", method = RequestMethod.GET)
public class StatusController extends ExceptionHandlingController {

    private final static Logger LOG = LoggerFactory.getLogger(StatusController.class);

    @Autowired
    private CompanyDAO companyDAO;

    /**
     * Loads the status screen that displays a list of subscribed companies and users.
     * 
     * @return the next model-and-view
     */
    @RequestMapping
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
