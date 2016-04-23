package com.justbuyit.eventprocessor;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import com.justbuyit.eventprocessor.subscription.CancelSubscriptionEventProcessor;
import com.justbuyit.eventprocessor.subscription.ChangeSubscriptionEventProcessor;
import com.justbuyit.eventprocessor.subscription.CreateSubscriptionEventProcessor;
import com.justbuyit.eventprocessor.subscription.NotifySubscriptionEventProcessor;
import com.justbuyit.eventprocessor.user.AssignUserEventProcessor;
import com.justbuyit.eventprocessor.user.UnassignUserEventProcessor;
import com.justbuyit.model.event.EventType;

/**
 * Unit tests for {@link EventProcessorFactory}.
 */
@RunWith(MockitoJUnitRunner.class)
public class EventProcessorFactoryTest {

    @Mock
    ApplicationContext applicationContext;
    
    @InjectMocks
    EventProcessorFactory eventProcessorFactory = new EventProcessorFactory();
    
    /**
     * Tests creation of specific event processor objects as per their event type.
     */
    @Test
    public void testCreateEventProcessor() {
        eventProcessorFactory.setApplicationContext(applicationContext);
        
        Assert.assertEquals(CreateSubscriptionEventProcessor.class, eventProcessorFactory.createEventProcessor(EventType.SUBSCRIPTION_ORDER).getClass());
        Assert.assertEquals(ChangeSubscriptionEventProcessor.class, eventProcessorFactory.createEventProcessor(EventType.SUBSCRIPTION_CHANGE).getClass());
        Assert.assertEquals(CancelSubscriptionEventProcessor.class, eventProcessorFactory.createEventProcessor(EventType.SUBSCRIPTION_CANCEL).getClass());
        Assert.assertEquals(NotifySubscriptionEventProcessor.class, eventProcessorFactory.createEventProcessor(EventType.SUBSCRIPTION_NOTICE).getClass());
        Assert.assertEquals(AssignUserEventProcessor.class, eventProcessorFactory.createEventProcessor(EventType.USER_ASSIGNMENT).getClass());
        Assert.assertEquals(UnassignUserEventProcessor.class, eventProcessorFactory.createEventProcessor(EventType.USER_UNASSIGNMENT).getClass());
    }

    /**
     * Tests that creation fails if the event type is not supplied.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateEventProcessorWithNullType() {
        eventProcessorFactory.createEventProcessor(null);
    }
    
}
