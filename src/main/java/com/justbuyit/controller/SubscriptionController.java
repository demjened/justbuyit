package com.justbuyit.controller;

import javax.servlet.http.HttpServletRequest;

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

/**
 * Controller for AppDirect-initiated subscription events.
 */
@RestController
@RequestMapping(value = "/subscription", method = RequestMethod.GET, produces = "application/xml")
public class SubscriptionController extends ExceptionHandlingController {

    private final static Logger LOG = LoggerFactory.getLogger(SubscriptionController.class);

    @Autowired
    private EventProcessorFactory eventProcessorFactory;

    /**
     * Creates a subscription as per pre-stashed event details, which is located at the given URL.
     * 
     * @param urlStr
     *            the event URL
     * @param req
     *            the request
     * @return the result object
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/create")
    public Result create(@RequestParam("url") String urlStr, HttpServletRequest req) throws Exception {
        LOG.info("/subscription/create :: {}", urlStr);

        // delegate processing to event type specific processor
        EventProcessor<?> processor = eventProcessorFactory.createEventProcessor(EventType.SUBSCRIPTION_ORDER);
        return processor.process(req, urlStr);
    }

    /**
     * Changes a subscription as per pre-stashed event details, which is located at the given URL.
     * 
     * @param urlStr
     *            the event URL
     * @return the result object
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/change")
    public Result change(@RequestParam("url") String urlStr, HttpServletRequest req) throws Exception {
        LOG.info("/subscription/change :: {}", urlStr);

        // delegate processing to event type specific processor
        EventProcessor<?> processor = eventProcessorFactory.createEventProcessor(EventType.SUBSCRIPTION_CHANGE);
        return processor.process(req, urlStr);
    }

    /**
     * Cancels a subscription as per pre-stashed event details, which is located at the given URL.
     * 
     * @param urlStr
     *            the event URL
     * @return the result object
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/cancel")
    public Result cancel(@RequestParam("url") String urlStr, HttpServletRequest req) throws Exception {
        LOG.info("/subscription/cancel :: {}", urlStr);

        // delegate processing to event type specific processor
        EventProcessor<?> processor = eventProcessorFactory.createEventProcessor(EventType.SUBSCRIPTION_CANCEL);
        return processor.process(req, urlStr);
    }

    /**
     * Issues a subscription status notification as per pre-stashed event details, which is located at the given URL.
     * 
     * @param urlStr
     *            the event URL
     * @return the result object
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/status")
    public Result status(@RequestParam("url") String urlStr, HttpServletRequest req) throws Exception {
        LOG.info("/subscription/status :: {}", urlStr);

        // delegate processing to event type specific processor
        EventProcessor<?> processor = eventProcessorFactory.createEventProcessor(EventType.SUBSCRIPTION_NOTICE);
        return processor.process(req, urlStr);
    }

}
