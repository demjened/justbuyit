package com.justbuyit.eventprocessor.subscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.subscription.CreateSubscriptionEvent;
import com.justbuyit.model.event.subscription.CreateSubscriptionEvent.CreateSubscriptionPayload;
import com.justbuyit.model.result.Result;

public class CreateSubscriptionEventProcessor extends EventProcessor<CreateSubscriptionEvent> {

    private final static Logger LOG = LoggerFactory.getLogger(CreateSubscriptionEventProcessor.class);
    
    private CompanyDAO companyDAO;
    
    public CreateSubscriptionEventProcessor(ConnectionSigner connectionSigner, CompanyDAO companyDAO) {
        super(connectionSigner);
        this.companyDAO = companyDAO;
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class[] { CreateSubscriptionEvent.class, CreateSubscriptionPayload.class };
    }

    @Override
    protected Result processEvent(CreateSubscriptionEvent event) throws JustBuyItException {
        LOG.debug("Processing event [{}]", event);
        
        // register company
        companyDAO.add(event.getPayload().getCompany());
        
        // create subscription
        // subscriptionDAO.createSubscription

        // assign user
        // assignmentDAO.assignUser
        
        return Result.successResult(String.format("Created subscription for company [%s]", event.getPayload().getCompany().getName()));
    }
    
}
