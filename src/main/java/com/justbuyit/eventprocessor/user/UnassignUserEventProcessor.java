package com.justbuyit.eventprocessor.user;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.user.UnassignUserEvent;
import com.justbuyit.model.event.user.UnassignUserEvent.UnassignUserPayload;
import com.justbuyit.model.result.Result;

public class UnassignUserEventProcessor extends EventProcessor<UnassignUserEvent> {

    private UserDAO userDAO;

    public UnassignUserEventProcessor(ConnectionSigner connectionSigner, UserDAO userDAO) {
        super(connectionSigner);
        this.userDAO = userDAO;
    }

    @Override
    protected Result processEvent(UnassignUserEvent event) throws JustBuyItException {
        String id = event.getPayload().getAccount().getAccountIdentifier();

        // unassign user
        userDAO.unassign(event.getPayload().getUser(), id);
        
        Result result = new Result();
        result.setMessage(String.format("Unassigned ... subscription for company [%s]", id));
        return result;
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class<?>[] { UnassignUserEvent.class, UnassignUserPayload.class };
    }

}
