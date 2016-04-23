package com.justbuyit.eventprocessor.subscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.entity.Company;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.subscription.CancelSubscriptionEvent;
import com.justbuyit.model.event.subscription.CancelSubscriptionEvent.CancelSubscriptionPayload;
import com.justbuyit.model.result.Result;

public class CancelSubscriptionEventProcessor extends EventProcessor<CancelSubscriptionEvent> {

    private final static Logger LOG = LoggerFactory.getLogger(CancelSubscriptionEventProcessor.class);
    
    private CompanyDAO companyDAO;

    public CancelSubscriptionEventProcessor(ConnectionSigner connectionSigner, CompanyDAO companyDAO) {
        super(connectionSigner);
        this.companyDAO = companyDAO;
    }

    @Override
    protected Result processEvent(CancelSubscriptionEvent event) throws JustBuyItException {
        LOG.debug("Processing event [{}]", event);

        String companyId = event.getPayload().getAccount().getAccountIdentifier();
        
        // delete company and users
        Company company = companyDAO.findById(companyId);
        companyDAO.delete(company);
        
        return Result.successResult(String.format("Cancelled subscription for company [%s]", companyId));
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class<?>[] { CancelSubscriptionEvent.class, CancelSubscriptionPayload.class };
    }

}
