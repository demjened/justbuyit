package com.justbuyit.eventprocessor.subscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.dao.SubscriptionDAO;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.subscription.NotifySubscriptionEvent;
import com.justbuyit.model.event.subscription.NotifySubscriptionEvent.NotifySubscriptionPayload;
import com.justbuyit.model.result.Result;

public class NotifySubscriptionEventProcessor extends EventProcessor<NotifySubscriptionEvent> {

    private final static Logger LOG = LoggerFactory.getLogger(NotifySubscriptionEventProcessor.class);
    
    private CompanyDAO companyDAO;
    private SubscriptionDAO subscriptionDAO;
    private UserDAO userDAO;

    public NotifySubscriptionEventProcessor(ConnectionSigner connectionSigner, CompanyDAO companyDAO, SubscriptionDAO subscriptionDAO, UserDAO userDAO) {
        super(connectionSigner);
        this.companyDAO = companyDAO;
        this.subscriptionDAO = subscriptionDAO;
        this.userDAO = userDAO;
    }

    @Override
    protected Result processEvent(NotifySubscriptionEvent event) throws JustBuyItException {
        LOG.debug("Processing event [{}]", event);

        String companyId = event.getPayload().getAccount().getAccountIdentifier();
        String noticeType = event.getPayload().getNotice().getType();
        if (noticeType.equals("CLOSED")) {
            // cancel subscription
            subscriptionDAO.delete(companyId);

            // delete company
            companyDAO.delete(companyId);

            // remove all users
            userDAO.deleteAll(companyId);
        } else if (!noticeType.equals("UPCOMING_INVOICE")) {
            // update subscription status
            String subscriptionStatus = event.getPayload().getAccount().getStatus();
            companyDAO.updateSubscriptionStatus(companyId, subscriptionStatus);
        }

        return Result.successResult(String.format("Changed subscription for company [%s]", companyId));
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class<?>[] { NotifySubscriptionEvent.class, NotifySubscriptionPayload.class };
    }

}
