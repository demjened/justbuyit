package com.justbuyit.eventprocessor.subscription;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.entity.Company;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.AccountNotFoundException;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.subscription.NotifySubscriptionEvent;
import com.justbuyit.model.event.subscription.NotifySubscriptionEvent.NotifySubscriptionPayload;
import com.justbuyit.model.result.Result;

/**
 * Event processor for subsciption notify events.
 */
public class NotifySubscriptionEventProcessor extends EventProcessor<NotifySubscriptionEvent> {

    private CompanyDAO companyDAO;

    public NotifySubscriptionEventProcessor(ConnectionSigner connectionSigner, CompanyDAO companyDAO) {
        super(connectionSigner);
        this.companyDAO = companyDAO;
    }

    @Override
    protected Result processEvent(NotifySubscriptionEvent event) throws JustBuyItException {
        String companyId = event.getPayload().getAccount().getAccountIdentifier();
        String noticeType = event.getPayload().getNotice().getType();
        if (noticeType.equals("CLOSED")) {

            // fetch company
            Company company = companyDAO.findById(companyId);
            if (company == null) {
                throw new AccountNotFoundException(String.format("Could not find account [%s]", companyId));
            }
            
            // delete company/subscription
            companyDAO.delete(company);
            
        } else if (!noticeType.equals("UPCOMING_INVOICE")) {
            
            // fetch company
            Company company = companyDAO.findById(companyId);
            if (company == null) {
                throw new AccountNotFoundException(String.format("Could not find account [%s]", companyId));
            }

            // update subscription status
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
