package com.justbuyit.eventprocessor.subscription;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.entity.Company;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.AccountNotFoundException;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.subscription.CancelSubscriptionEvent;
import com.justbuyit.model.event.subscription.CancelSubscriptionEvent.CancelSubscriptionPayload;
import com.justbuyit.model.result.Result;

/**
 * Event processor for subsciption cancel events.
 */
public class CancelSubscriptionEventProcessor extends EventProcessor<CancelSubscriptionEvent> {

    private CompanyDAO companyDAO;

    public CancelSubscriptionEventProcessor(ConnectionSigner connectionSigner, CompanyDAO companyDAO) {
        super(connectionSigner);
        this.companyDAO = companyDAO;
    }

    @Override
    protected Result processEvent(CancelSubscriptionEvent event) throws JustBuyItException {
        // fetch company
        String companyId = event.getPayload().getAccount().getAccountIdentifier();
        Company company = companyDAO.findById(companyId);
        if (company == null) {
            throw new AccountNotFoundException(String.format("Could not find account [%s]", companyId));
        }
        
        // delete company and users
        companyDAO.delete(company);
        
        return Result.successResult(String.format("Cancelled subscription for company [%s]", companyId));
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class<?>[] { CancelSubscriptionEvent.class, CancelSubscriptionPayload.class };
    }

}
