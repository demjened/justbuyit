package com.justbuyit.eventprocessor.subscription;

import com.justbuyit.auth.OAuthService;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.entity.Company;
import com.justbuyit.entity.User;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.exception.UserAlreadyExistsException;
import com.justbuyit.model.event.subscription.CreateSubscriptionEvent;
import com.justbuyit.model.event.subscription.CreateSubscriptionEvent.CreateSubscriptionPayload;
import com.justbuyit.model.result.Result;

/**
 * Event processor for subsciption create events.
 */
public class CreateSubscriptionEventProcessor extends EventProcessor<CreateSubscriptionEvent> {

    private CompanyDAO companyDAO;
    
    public CreateSubscriptionEventProcessor(OAuthService oAuthService, CompanyDAO companyDAO) {
        super(oAuthService);
        this.companyDAO = companyDAO;
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class[] { CreateSubscriptionEvent.class, CreateSubscriptionPayload.class };
    }

    @Override
    protected Result processEvent(CreateSubscriptionEvent event) throws JustBuyItException {
        // check if company exists
        String companyId = event.getPayload().getCompany().getUuid();
        if (companyDAO.findById(companyId) != null) {
            throw new UserAlreadyExistsException(String.format("Account [%s] already exists", companyId));
        }
        
        // register company
        Company company = companyDAO.create(new Company(event.getPayload().getCompany()));
        
        // set subscription attributes
        company.setSubscriptionEditionCode(event.getPayload().getOrder().getEditionCode());
        company.setSubscriptionStatus("ACTIVE");
        
        // assign creator user to the company
        User creator = new User(event.getCreator());
        creator.setCompany(company);
        company.getUsers().add(creator);
        companyDAO.update(company);
        
        return Result.successResult(String.format("Created subscription for company [%s], user [%s] has been assigned", event.getPayload().getCompany().getName(), event.getCreator().getOpenId()), company.getUuid());
    }
    
}
