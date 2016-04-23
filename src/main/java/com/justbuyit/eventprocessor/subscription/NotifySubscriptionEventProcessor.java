package com.justbuyit.eventprocessor.subscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.entity.Company;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.subscription.NotifySubscriptionEvent;
import com.justbuyit.model.event.subscription.NotifySubscriptionEvent.NotifySubscriptionPayload;
import com.justbuyit.model.result.Result;

public class NotifySubscriptionEventProcessor extends EventProcessor<NotifySubscriptionEvent> {

    private final static Logger LOG = LoggerFactory.getLogger(NotifySubscriptionEventProcessor.class);
    
    private CompanyDAO companyDAO;

    public NotifySubscriptionEventProcessor(ConnectionSigner connectionSigner, CompanyDAO companyDAO) {
        super(connectionSigner);
        this.companyDAO = companyDAO;
    }

    @Override
    protected Result processEvent(NotifySubscriptionEvent event) throws JustBuyItException {
        LOG.debug("Processing event [{}]", event);

        String companyId = event.getPayload().getAccount().getAccountIdentifier();
        String noticeType = event.getPayload().getNotice().getType();
        if (noticeType.equals("CLOSED")) {

            // delete company/subscription
            Company company = companyDAO.findById(companyId);
            companyDAO.delete(company);
        } else if (!noticeType.equals("UPCOMING_INVOICE")) {
            
            // update subscription status
            Company company = companyDAO.findById(companyId);
            company.setSubscriptionStatus(event.getPayload().getAccount().getStatus());
            companyDAO.update(company);
        }

        return Result.successResult(String.format("Changed subscription for company [%s]", companyId));
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class<?>[] { NotifySubscriptionEvent.class, NotifySubscriptionPayload.class };
    }

}
