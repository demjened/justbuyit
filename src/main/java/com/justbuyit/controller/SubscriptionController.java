package com.justbuyit.controller;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.model.CreateSubscriptionEvent;
import com.justbuyit.model.Result;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    private final static Logger LOG = LoggerFactory.getLogger(SubscriptionController.class);

    @Autowired
    private ConnectionSigner connectionSigner;
    
    @RequestMapping(value="/create", method = RequestMethod.GET, produces = "application/xml")
    public Result create(@RequestParam("url") String urlStr) throws Exception {
        LOG.info("/subscription/create :: {}", urlStr);
        
        // sign URL and connect
        URL url = new URL(urlStr);
        HttpURLConnection conn = connectionSigner.openSignedConnection(url);
        
        // process response - TODO: encapsulate in class hierarchy
        InputStream is = conn.getInputStream();
        JAXBContext jaxbContext = JAXBContext.newInstance(CreateSubscriptionEvent.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        CreateSubscriptionEvent cse = (CreateSubscriptionEvent) unmarshaller.unmarshal(is);
        is.close();
        
        Result result = new Result();
        try {
            String companyId = cse.getPayload().getCompany().getUuid();
    
            // register company
            // create subscription
            // assign user
        
            result.setSuccess(true);
            result.setMessage(String.format("Created subscription for company [%s]", companyId));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            // TODO: use exception handler
            // TODO: errorcode from exception subclass
        }
        
        return result;
    }
    
    @RequestMapping(value="/change", method = RequestMethod.GET, produces = "application/xml")
    public Result change(@RequestParam("url") String urlStr) throws Exception {
        LOG.info("/subscription/change :: {}", urlStr);
        
        // sign URL and connect
        URL url = new URL(urlStr);
        HttpURLConnection conn = connectionSigner.openSignedConnection(url);
        
        return null;
    }

    @RequestMapping(value="/cancel", method = RequestMethod.GET, produces = "application/xml")
    public Result cancel(@RequestParam("url") String urlStr) throws Exception {
        LOG.info("/subscription/cancel :: {}", urlStr);

        // TODO
        return null;
    }
    
    @RequestMapping(value="/status", method = RequestMethod.GET, produces = "application/xml")
    public Result status(@RequestParam("url") String urlStr) throws Exception {
        LOG.info("/subscription/status :: {}", urlStr);

        // TODO
        return null;
    }
    
}
