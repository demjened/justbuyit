package com.justbuyit.eventprocessor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.justbuyit.auth.OAuthService;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.eventprocessor.subscription.CancelSubscriptionEventProcessor;
import com.justbuyit.eventprocessor.subscription.ChangeSubscriptionEventProcessor;
import com.justbuyit.eventprocessor.subscription.CreateSubscriptionEventProcessor;
import com.justbuyit.eventprocessor.subscription.NotifySubscriptionEventProcessor;
import com.justbuyit.eventprocessor.user.AssignUserEventProcessor;
import com.justbuyit.eventprocessor.user.UnassignUserEventProcessor;
import com.justbuyit.model.event.EventType;

/**
 * Factory of {@link EventProcessor} objects.
 */
@Component
public class EventProcessorFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * Instantiates the specific {@link EventProcessor} object related to the given event type.
     * 
     * @param type
     *            the event type
     * @return the event processor
     */
    public EventProcessor<?> createEventProcessor(EventType type) {
        Assert.notNull(type);

        switch (type) {
        case SUBSCRIPTION_ORDER:
            return new CreateSubscriptionEventProcessor(
                    applicationContext.getBean(OAuthService.class), 
                    applicationContext.getBean(CompanyDAO.class));
        case SUBSCRIPTION_CHANGE:
            return new ChangeSubscriptionEventProcessor(
                    applicationContext.getBean(OAuthService.class), 
                    applicationContext.getBean(CompanyDAO.class)); 
        case SUBSCRIPTION_CANCEL:
            return new CancelSubscriptionEventProcessor(
                    applicationContext.getBean(OAuthService.class),
                    applicationContext.getBean(CompanyDAO.class));
        case SUBSCRIPTION_NOTICE:
            return new NotifySubscriptionEventProcessor(
                    applicationContext.getBean(OAuthService.class),
                    applicationContext.getBean(CompanyDAO.class));
        case USER_ASSIGNMENT:
            return new AssignUserEventProcessor(
                    applicationContext.getBean(OAuthService.class),
                    applicationContext.getBean(CompanyDAO.class));
        case USER_UNASSIGNMENT:
            return new UnassignUserEventProcessor(
                    applicationContext.getBean(OAuthService.class),
                    applicationContext.getBean(CompanyDAO.class));
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
