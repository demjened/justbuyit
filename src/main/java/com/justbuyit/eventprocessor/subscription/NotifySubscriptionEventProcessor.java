package com.justbuyit.eventprocessor.subscription;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.dao.SubscriptionDAO;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.subscription.NotifySubscriptionEvent;
import com.justbuyit.model.event.subscription.NotifySubscriptionEvent.NotifySubscriptionPayload;
import com.justbuyit.model.result.Result;

public class NotifySubscriptionEventProcessor extends EventProcessor<NotifySubscriptionEvent> {

    private CompanyDAO companyDAO;
    private SubscriptionDAO subscriptionDAO;

    public NotifySubscriptionEventProcessor(ConnectionSigner connectionSigner, CompanyDAO companyDAO, SubscriptionDAO subscriptionDAO) {
        super(connectionSigner);
        this.companyDAO = companyDAO;
        this.subscriptionDAO = subscriptionDAO;
    }

    @Override
    protected Result processEvent(NotifySubscriptionEvent event) throws JustBuyItException {
        String id = event.getPayload().getAccount().getAccountIdentifier();
        String noticeType = event.getPayload().getNotice().getType();
        if(noticeType.equals("CLOSED")){
            // cancel subscription
            subscriptionDAO.cancel(id);
            
            // delete company
            companyDAO.delete(id);
            
            // userDao.unassignEntireCustomer(customerId);
        } else if (!noticeType.equals("UPCOMING_INVOICE")) {
            //String customerStatus = noticeSubscriptionEvent.getPayload().getAccount().getStatus();
            //customerDao.updateCustomerSubscriptionStatus(customerId, SubscriptionStatus.valueOf(customerStatus));
            
        }

        Result result = new Result();
        result.setMessage(String.format("Cancelled subscription for company [%s]", id));
        return result;
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class<?>[] { NotifySubscriptionEvent.class, NotifySubscriptionPayload.class };
    }

}
