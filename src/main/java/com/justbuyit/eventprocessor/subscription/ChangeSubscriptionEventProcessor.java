package com.justbuyit.eventprocessor.subscription;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.entity.Company;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.AccountNotFoundException;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.subscription.ChangeSubscriptionEvent;
import com.justbuyit.model.event.subscription.ChangeSubscriptionEvent.ChangeSubscriptionPayload;
import com.justbuyit.model.result.Result;

/**
 * Event processor for subsciption change events.
 */
public class ChangeSubscriptionEventProcessor extends EventProcessor<ChangeSubscriptionEvent> {

    private CompanyDAO companyDAO;
    
    public ChangeSubscriptionEventProcessor(ConnectionSigner connectionSigner, CompanyDAO companyDAO) {
        super(connectionSigner);
        this.companyDAO = companyDAO;
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class[] { ChangeSubscriptionEvent.class, ChangeSubscriptionPayload.class };
    }

    @Override
    protected Result processEvent(ChangeSubscriptionEvent event) throws JustBuyItException {
        // fetch company
        String companyId = event.getPayload().getAccount().getAccountIdentifier();
        Company company = companyDAO.findById(companyId);
        if (company == null) {
            throw new AccountNotFoundException(String.format("Could not find account [%s]", companyId));
        }
        
        // update subscription
        company.setSubscriptionEditionCode(event.getPayload().getOrder().getEditionCode());
        companyDAO.update(company);
        
        return Result.successResult(String.format("Changed subscription for company [%s]", event.getPayload().getAccount().getAccountIdentifier()));
    }
    
}
