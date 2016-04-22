package com.justbuyit.eventprocessor.subscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.dao.SubscriptionDAO;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.subscription.CancelSubscriptionEvent;
import com.justbuyit.model.event.subscription.CancelSubscriptionEvent.CancelSubscriptionPayload;
import com.justbuyit.model.result.Result;

public class CancelSubscriptionEventProcessor extends EventProcessor<CancelSubscriptionEvent> {

    private final static Logger LOG = LoggerFactory.getLogger(CancelSubscriptionEventProcessor.class);
    
    private CompanyDAO companyDAO;
    private SubscriptionDAO subscriptionDAO;
    private UserDAO userDAO;

    public CancelSubscriptionEventProcessor(ConnectionSigner connectionSigner, CompanyDAO companyDAO, SubscriptionDAO subscriptionDAO, UserDAO userDAO) {
        super(connectionSigner);
        this.companyDAO = companyDAO;
        this.subscriptionDAO = subscriptionDAO;
        this.userDAO = userDAO;
    }

    @Override
    protected Result processEvent(CancelSubscriptionEvent event) throws JustBuyItException {
        LOG.debug("Processing event [{}]", event);

        String companyId = event.getPayload().getAccount().getAccountIdentifier();
        
        // cancel subscription
        subscriptionDAO.delete(companyId);
        
        // delete company
        companyDAO.delete(companyId);
        
        // remove all users
        userDAO.removeAll(companyId);

        return Result.successResult(String.format("Cancelled subscription for company [%s]", companyId));
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class<?>[] { CancelSubscriptionEvent.class, CancelSubscriptionPayload.class };
    }

}
