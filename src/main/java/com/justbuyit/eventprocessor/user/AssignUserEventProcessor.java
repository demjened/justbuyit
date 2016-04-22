package com.justbuyit.eventprocessor.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.user.AssignUserEvent;
import com.justbuyit.model.event.user.AssignUserEvent.AssignUserPayload;
import com.justbuyit.model.result.Result;

public class AssignUserEventProcessor extends EventProcessor<AssignUserEvent> {

    private final static Logger LOG = LoggerFactory.getLogger(AssignUserEventProcessor.class);

    private UserDAO userDAO;

    public AssignUserEventProcessor(ConnectionSigner connectionSigner, UserDAO userDAO) {
        super(connectionSigner);
        this.userDAO = userDAO;
    }

    @Override
    protected Result processEvent(AssignUserEvent event) throws JustBuyItException {
        LOG.debug("Processing event [{}]", event);

        String companyId = event.getPayload().getAccount().getAccountIdentifier();

        // assign user to company
        userDAO.assign(event.getPayload().getUser(), companyId);
        
        return Result.successResult(String.format("Assigned user [%s] to company [%s]", event.getPayload().getUser().getUuid(), companyId));
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class<?>[] { AssignUserEvent.class, AssignUserPayload.class };
    }

}
