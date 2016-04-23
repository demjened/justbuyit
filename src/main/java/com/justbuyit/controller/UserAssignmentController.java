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

/**
 * Controller for AppDirect-initiated user assignment events.
 */
@RestController
@RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/xml")
public class UserAssignmentController extends ExceptionHandlingController {

    private final static Logger LOG = LoggerFactory.getLogger(UserAssignmentController.class);

    @Autowired
    private EventProcessorFactory eventProcessorFactory;

    /**
     * Assigns a user as per pre-stashed event details, which is located at the given URL.
     * 
     * @param urlStr
     *            the event URL
     * @return the result object
     * @throws Exception
     */
    @RequestMapping("/assignment")
    public Result assignment(@RequestParam("url") String urlStr) throws Exception {
        LOG.info("/user/assignment :: {}", urlStr);

        // delegate processing to event type specific processor
        EventProcessor<?> processor = eventProcessorFactory.createEventProcessor(EventType.USER_ASSIGNMENT);
        return processor.process(urlStr);
    }

    /**
     * Assigns a user as per pre-stashed event details, which is located at the given URL.
     * 
     * @param urlStr
     *            the event URL
     * @return the result object
     * @throws Exception
     */
    @RequestMapping("/unassignment")
    public Result unassignment(@RequestParam("url") String urlStr) throws Exception {
        LOG.info("/user/unassignment :: {}", urlStr);

        // delegate processing to event type specific processor
        EventProcessor<?> processor = eventProcessorFactory.createEventProcessor(EventType.USER_UNASSIGNMENT);
        return processor.process(urlStr);
    }

}
