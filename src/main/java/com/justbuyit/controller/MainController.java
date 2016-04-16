package com.justbuyit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/hello")
public class MainController {

    @RequestMapping(value="{name}", method = RequestMethod.GET)
    public ModelAndView sayHello(@PathVariable String name) {
        
        return new ModelAndView("hello", "name", name);
    }
    
}
