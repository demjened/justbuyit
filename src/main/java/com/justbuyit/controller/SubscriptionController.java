package com.justbuyit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.eventprocessor.EventProcessorFactory;
import com.justbuyit.model.event.EventType;
import com.justbuyit.model.result.Result;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    private final static Logger LOG = LoggerFactory.getLogger(SubscriptionController.class);

    @Autowired
    private EventProcessorFactory eventProcessorFactory;

    @ResponseBody
    @RequestMapping(value="/create", method = RequestMethod.GET, produces = "application/xml")
    public Result create(@RequestParam("url") String urlStr) throws Exception {
        LOG.info("/subscription/create :: {}", urlStr);
        
        EventProcessor<?> processor = eventProcessorFactory.createEventProcessor(EventType.SUBSCRIPTION_ORDER);
        return processor.process(urlStr);
    }
    
    @ResponseBody
    @RequestMapping(value="/change", method = RequestMethod.GET, produces = "application/xml")
    public Result change(@RequestParam("url") String urlStr) throws Exception {
        LOG.info("/subscription/change :: {}", urlStr);
        
        EventProcessor<?> processor = eventProcessorFactory.createEventProcessor(EventType.SUBSCRIPTION_CHANGE);
        return processor.process(urlStr);
    }

    @ResponseBody
    @RequestMapping(value="/cancel", method = RequestMethod.GET, produces = "application/xml")
    public Result cancel(@RequestParam("url") String urlStr) throws Exception {
        LOG.info("/subscription/cancel :: {}", urlStr);

        EventProcessor<?> processor = eventProcessorFactory.createEventProcessor(EventType.SUBSCRIPTION_CANCEL);
        return processor.process(urlStr);
    }
    
    @ResponseBody
    @RequestMapping(value="/status", method = RequestMethod.GET, produces = "application/xml")
    public Result status(@RequestParam("url") String urlStr) throws Exception {
        LOG.info("/subscription/status :: {}", urlStr);

        EventProcessor<?> processor = eventProcessorFactory.createEventProcessor(EventType.SUBSCRIPTION_NOTICE);
        return processor.process(urlStr);
    }
    
}
