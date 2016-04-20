package com.justbuyit.eventprocessor.subscription;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.dao.SubscriptionDAO;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.subscription.CancelSubscriptionEvent;
import com.justbuyit.model.event.subscription.CancelSubscriptionEvent.CancelSubscriptionPayload;
import com.justbuyit.model.result.Result;

public class CancelSubscriptionEventProcessor extends EventProcessor<CancelSubscriptionEvent> {

    private CompanyDAO companyDAO;
    private SubscriptionDAO subscriptionDAO;

    public CancelSubscriptionEventProcessor(ConnectionSigner connectionSigner, CompanyDAO companyDAO, SubscriptionDAO subscriptionDAO) {
        super(connectionSigner);
        this.companyDAO = companyDAO;
        this.subscriptionDAO = subscriptionDAO;
    }

    @Override
    protected Result processEvent(CancelSubscriptionEvent event) throws JustBuyItException {
        String id = event.getPayload().getAccount().getAccountIdentifier();
        
        // cancel subscription
        subscriptionDAO.cancel(id);
        
        // delete company
        companyDAO.delete(id);
        
        // userDao.unassignEntireCustomer(customerId);

        Result result = new Result();
        result.setMessage(String.format("Cancelled subscription for company [%s]", id));
        return result;
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class<?>[] { CancelSubscriptionEvent.class, CancelSubscriptionPayload.class };
    }

}
