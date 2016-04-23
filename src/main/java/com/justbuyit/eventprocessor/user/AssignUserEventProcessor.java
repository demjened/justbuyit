package com.justbuyit.eventprocessor.user;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.entity.Company;
import com.justbuyit.entity.User;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.AccountNotFoundException;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.exception.UserAlreadyExistsException;
import com.justbuyit.model.event.user.AssignUserEvent;
import com.justbuyit.model.event.user.AssignUserEvent.AssignUserPayload;
import com.justbuyit.model.result.Result;

/**
 * Event processor for user assignment events.
 */
public class AssignUserEventProcessor extends EventProcessor<AssignUserEvent> {

    private CompanyDAO companyDAO;

    public AssignUserEventProcessor(ConnectionSigner connectionSigner, CompanyDAO companyDAO) {
        super(connectionSigner);
        this.companyDAO = companyDAO;
    }

    @Override
    protected Result processEvent(AssignUserEvent event) throws JustBuyItException {
        // fetch company
        String companyId = event.getPayload().getAccount().getAccountIdentifier();
        Company company = companyDAO.findById(companyId);
        if (company == null) {
            throw new AccountNotFoundException(String.format("Could not find account [%s]", companyId));
        }

        // check if user is already assigned
        User user = new User(event.getPayload().getUser());
        if (company.getUsers().contains(user)) {
            throw new UserAlreadyExistsException(String.format("User [%s] is already assigned to account [%s]", user.getUuid(), companyId));
        }
        
        // assign user to company
        user.setCompany(company);
        company.getUsers().add(user);
        companyDAO.update(company);
        
        return Result.successResult(String.format("Assigned user [%s] to company [%s]", user.getUuid(), companyId));
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class<?>[] { AssignUserEvent.class, AssignUserPayload.class };
    }

}
