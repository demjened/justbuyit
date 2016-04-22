package com.justbuyit.eventprocessor.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.user.UnassignUserEvent;
import com.justbuyit.model.event.user.UnassignUserEvent.UnassignUserPayload;
import com.justbuyit.model.result.Result;

public class UnassignUserEventProcessor extends EventProcessor<UnassignUserEvent> {

    private final static Logger LOG = LoggerFactory.getLogger(UnassignUserEventProcessor.class);
    
    private UserDAO userDAO;

    public UnassignUserEventProcessor(ConnectionSigner connectionSigner, UserDAO userDAO) {
        super(connectionSigner);
        this.userDAO = userDAO;
    }

    @Override
    protected Result processEvent(UnassignUserEvent event) throws JustBuyItException {
        LOG.debug("Processing event [{}]", event);

        String companyId = event.getPayload().getAccount().getAccountIdentifier();

        // unassign user
        userDAO.unassign(event.getPayload().getUser(), companyId);
        
        return Result.successResult(String.format("Unassigned user [%s] from company [%s]", event.getPayload().getUser().getUuid(), companyId));
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class<?>[] { UnassignUserEvent.class, UnassignUserPayload.class };
    }

}
