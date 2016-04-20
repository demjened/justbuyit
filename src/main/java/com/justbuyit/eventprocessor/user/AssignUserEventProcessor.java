package com.justbuyit.eventprocessor.user;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.eventprocessor.EventProcessor;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.user.AssignUserEvent;
import com.justbuyit.model.event.user.AssignUserEvent.AssignUserPayload;
import com.justbuyit.model.result.Result;

public class AssignUserEventProcessor extends EventProcessor<AssignUserEvent> {

    private UserDAO userDAO;

    public AssignUserEventProcessor(ConnectionSigner connectionSigner, UserDAO userDAO) {
        super(connectionSigner);
        this.userDAO = userDAO;
    }

    @Override
    protected Result processEvent(AssignUserEvent event) throws JustBuyItException {
        String id = event.getPayload().getAccount().getAccountIdentifier();

        // assign user to company
        userDAO.assign(event.getPayload().getUser(), id);
        
        Result result = new Result();
        result.setMessage(String.format("Cancelled subscription for company [%s]", id));
        return result;
    }

    @Override
    protected Class<?>[] getClassesToBeBound() {
        return new Class<?>[] { AssignUserEvent.class, AssignUserPayload.class };
    }

}
