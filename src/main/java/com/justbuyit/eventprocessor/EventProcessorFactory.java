package com.justbuyit.eventprocessor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.dao.SubscriptionDAO;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.eventprocessor.subscription.CancelSubscriptionEventProcessor;
import com.justbuyit.eventprocessor.subscription.ChangeSubscriptionEventProcessor;
import com.justbuyit.eventprocessor.subscription.CreateSubscriptionEventProcessor;
import com.justbuyit.eventprocessor.subscription.NotifySubscriptionEventProcessor;
import com.justbuyit.eventprocessor.user.AssignUserEventProcessor;
import com.justbuyit.eventprocessor.user.UnassignUserEventProcessor;
import com.justbuyit.model.event.EventType;

@Component
public class EventProcessorFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    
    public EventProcessor<?> createEventProcessor(EventType type) {
        
        switch (type) {
        case SUBSCRIPTION_ORDER:
            return new CreateSubscriptionEventProcessor(
                    applicationContext.getBean(ConnectionSigner.class), 
                    applicationContext.getBean(CompanyDAO.class));
        case SUBSCRIPTION_CHANGE:
            return new ChangeSubscriptionEventProcessor(
                    applicationContext.getBean(ConnectionSigner.class), 
                    applicationContext.getBean(SubscriptionDAO.class)); 
        case SUBSCRIPTION_CANCEL:
            return new CancelSubscriptionEventProcessor(
                    applicationContext.getBean(ConnectionSigner.class),
                    applicationContext.getBean(CompanyDAO.class),
                    applicationContext.getBean(SubscriptionDAO.class));
        case SUBSCRIPTION_NOTICE:
            return new NotifySubscriptionEventProcessor(
                    applicationContext.getBean(ConnectionSigner.class),
                    applicationContext.getBean(CompanyDAO.class),
                    applicationContext.getBean(SubscriptionDAO.class));
        case USER_ASSIGNMENT:
            return new AssignUserEventProcessor(
                    applicationContext.getBean(ConnectionSigner.class),
                    applicationContext.getBean(UserDAO.class));
        case USER_UNASSIGNMENT:
            return new UnassignUserEventProcessor(
                    applicationContext.getBean(ConnectionSigner.class),
                    applicationContext.getBean(UserDAO.class));
        case USER_UPDATED:
        default:
            return null;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
