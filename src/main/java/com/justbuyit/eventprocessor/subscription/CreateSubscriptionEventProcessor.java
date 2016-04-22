package com.justbuyit.eventprocessor.subscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.dao.SubscriptionDAO;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.subscription.CreateSubscriptionEvent;
import com.justbuyit.model.event.subscription.CreateSubscriptionEvent.CreateSubscriptionPayload;
import com.justbuyit.model.result.Result;

public class CreateSubscriptionEventProcessor extends EventProcessor<CreateSubscriptionEvent> {

    private final static Logger LOG = LoggerFactory.getLogger(CreateSubscriptionEventProcessor.class);
    
    private CompanyDAO companyDAO;
    private SubscriptionDAO subscriptionDAO;
    private UserDAO userDAO;
    
    public CreateSubscriptionEventProcessor(ConnectionSigner connectionSigner, CompanyDAO companyDAO, SubscriptionDAO subscriptionDAO, UserDAO userDAO) {
        super(connectionSigner);
        this.companyDAO = companyDAO;
        this.subscriptionDAO = subscriptionDAO;
        this.userDAO = userDAO;
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class[] { CreateSubscriptionEvent.class, CreateSubscriptionPayload.class };
    }

    @Override
    protected Result processEvent(CreateSubscriptionEvent event) throws JustBuyItException {
        LOG.debug("Processing event [{}]", event);
        
        // register company
        String companyId = companyDAO.add(event.getPayload().getCompany());
        
        // create subscription
        subscriptionDAO.add(companyId, event.getPayload().getOrder());

        // assign creator user to the company
        userDAO.assign(event.getCreator(), companyId);
        
        return Result.successResult(String.format("Created subscription for company [%s], user [%s] has been assigned", event.getPayload().getCompany().getName(), event.getCreator().getOpenId()), companyId);
    }
    
}
