package com.justbuyit.eventprocessor.user;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.entity.Company;
import com.justbuyit.entity.User;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.AccountNotFoundException;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.exception.UserNotFoundException;
import com.justbuyit.model.event.user.UnassignUserEvent;
import com.justbuyit.model.event.user.UnassignUserEvent.UnassignUserPayload;
import com.justbuyit.model.result.Result;

/**
 * Event processor for user unassignment events.
 */
public class UnassignUserEventProcessor extends EventProcessor<UnassignUserEvent> {

    private CompanyDAO companyDAO;

    public UnassignUserEventProcessor(ConnectionSigner connectionSigner, CompanyDAO companyDAO) {
        super(connectionSigner);
        this.companyDAO = companyDAO;
    }

    @Override
    protected Result processEvent(UnassignUserEvent event) throws JustBuyItException {
        // fetch company
        String companyId = event.getPayload().getAccount().getAccountIdentifier();
        Company company = companyDAO.findById(companyId);
        if (company == null) {
            throw new AccountNotFoundException(String.format("Could not find account [%s]", companyId));
        }
        
        // check if user is assigned
        User user = new User(event.getPayload().getUser());
        if (!company.getUsers().contains(user)) {
            throw new UserNotFoundException(String.format("User [%s] is not assigned to account [%s]", user.getUuid(), companyId));
        }
        
        // unassign user from company
        company.getUsers().remove(user);
        companyDAO.update(company);
        
        return Result.successResult(String.format("Unassigned user [%s] from company [%s]", user.getUuid(), companyId));
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class<?>[] { UnassignUserEvent.class, UnassignUserPayload.class };
    }

}
