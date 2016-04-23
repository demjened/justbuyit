package com.justbuyit.eventprocessor.subscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.entity.Company;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.subscription.ChangeSubscriptionEvent;
import com.justbuyit.model.event.subscription.ChangeSubscriptionEvent.ChangeSubscriptionPayload;
import com.justbuyit.model.result.Result;

public class ChangeSubscriptionEventProcessor extends EventProcessor<ChangeSubscriptionEvent> {

    private final static Logger LOG = LoggerFactory.getLogger(ChangeSubscriptionEventProcessor.class);

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
        LOG.debug("Processing event [{}]", event);

        // change subscription
        Company company = companyDAO.findById(event.getPayload().getAccount().getAccountIdentifier());
        company.setSubscriptionEditionCode(event.getPayload().getOrder().getEditionCode());
        companyDAO.update(company);
        
        return Result.successResult(String.format("Changed subscription for company [%s]", event.getPayload().getAccount().getAccountIdentifier()));
    }
    
}
