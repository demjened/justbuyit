package com.justbuyit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Base controller that navigates to the error page upon encountering an unhandled exception.
 */
@Controller
public abstract class ExceptionHandlingController {

    private final static Logger LOG = LoggerFactory.getLogger(ExceptionHandlingController.class);

    /**
     * Handle the exception.
     * 
     * @param exception
     *            the exception
     * @return the next model-and-view
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(Exception exception) {
        LOG.error("Exception while processing request", exception);

        // navigate to the error page
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exception.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
