package com.justbuyit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/hello")
public class MainController {

    private final static Logger LOG = LoggerFactory.getLogger(MainController.class);
    
    @RequestMapping(value="/{name}", method = RequestMethod.GET)
    public ModelAndView hello(@PathVariable String name) {
        LOG.info("/hello {}", name);
        return new ModelAndView("hello", "name", name);
    }
    
}
