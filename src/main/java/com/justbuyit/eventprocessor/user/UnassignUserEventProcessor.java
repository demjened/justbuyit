package com.justbuyit.eventprocessor.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.entity.Company;
import com.justbuyit.entity.User;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.user.UnassignUserEvent;
import com.justbuyit.model.event.user.UnassignUserEvent.UnassignUserPayload;
import com.justbuyit.model.result.Result;

public class UnassignUserEventProcessor extends EventProcessor<UnassignUserEvent> {

    private final static Logger LOG = LoggerFactory.getLogger(UnassignUserEventProcessor.class);
    
    private CompanyDAO companyDAO;

    public UnassignUserEventProcessor(ConnectionSigner connectionSigner, CompanyDAO companyDAO) {
        super(connectionSigner);
        this.companyDAO = companyDAO;
    }

    @Override
    protected Result processEvent(UnassignUserEvent event) throws JustBuyItException {
        LOG.debug("Processing event [{}]", event);

        String companyId = event.getPayload().getAccount().getAccountIdentifier();

        // unassign user from company
        Company company = companyDAO.findById(companyId);
        company.getUsers().remove(new User(event.getPayload().getUser()));
        companyDAO.update(company);
        
        return Result.successResult(String.format("Unassigned user [%s] from company [%s]", event.getPayload().getUser().getUuid(), companyId));
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class<?>[] { UnassignUserEvent.class, UnassignUserPayload.class };
    }

}
