package com.justbuyit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.eventprocessor.EventProcessorFactory;
import com.justbuyit.model.event.EventType;
import com.justbuyit.model.result.Result;

@RestController
@RequestMapping("/user")
public class UserAssignmentController {

    private final static Logger LOG = LoggerFactory.getLogger(UserAssignmentController.class);

    @Autowired
    private EventProcessorFactory eventProcessorFactory;

    @RequestMapping(value="/assignment", method = RequestMethod.GET, produces = "application/xml")
    public Result assignment(@RequestParam("url") String urlStr) throws Exception {
        LOG.info("/user/assign :: {}", urlStr);
        
        EventProcessor<?> processor = eventProcessorFactory.createEventProcessor(EventType.USER_ASSIGNMENT);
        return processor.process(urlStr);
    }
    
    @RequestMapping(value="/unassignment", method = RequestMethod.GET, produces = "application/xml")
    public Result unassignment(@RequestParam("url") String urlStr) throws Exception {
        LOG.info("/user/unassignment :: {}", urlStr);
        
        EventProcessor<?> processor = eventProcessorFactory.createEventProcessor(EventType.USER_UNASSIGNMENT);
        return processor.process(urlStr);
    }
    
}
