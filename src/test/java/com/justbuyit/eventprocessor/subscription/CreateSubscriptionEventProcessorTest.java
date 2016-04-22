package com.justbuyit.eventprocessor.subscription;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.dao.SubscriptionDAO;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.exception.UserAlreadyExistsException;
import com.justbuyit.model.event.subscription.CreateSubscriptionEvent;
import com.justbuyit.model.result.Result;
import com.justbuyit.util.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class CreateSubscriptionEventProcessorTest {
    
    private final static String SAMPLE_FILE = "com/justbuyit/eventprocessor/subscription/createSubscription.xml";

    @Mock
    ConnectionSigner mockConnectionSigner;
    
    @Mock
    CompanyDAO mockCompanyDAO;
    
    @Mock
    SubscriptionDAO mockSubscriptionDAO;
    
    @Mock
    UserDAO mockUserDAO;
    
    @InjectMocks
    private CreateSubscriptionEventProcessor eventProcessor = new CreateSubscriptionEventProcessor(mockConnectionSigner, mockCompanyDAO, mockSubscriptionDAO, mockUserDAO);

    @Test
    public void testUnmarshal() throws Exception {
        Assert.assertEquals(CreateSubscriptionEvent.class, eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE)).getClass());
    }
    
    @Test
    public void testProcessEvent() throws Exception {
        Mockito.when(mockCompanyDAO.add(Matchers.anyObject())).thenReturn("123");
        
        CreateSubscriptionEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));
        Result result = eventProcessor.processEvent(event);
        
        // verify that the entities get created
        Mockito.verify(mockCompanyDAO).add(event.getPayload().getCompany());
        Mockito.verify(mockSubscriptionDAO).add("123", event.getPayload().getOrder());
        Mockito.verify(mockUserDAO).assign(event.getCreator(), "123");
        
        Assert.assertTrue(result.isSuccess());
    }
    
    @Test(expected = UserAlreadyExistsException.class)
    public void testProcessEventWhenCompanyExists() throws Exception {
        CreateSubscriptionEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));
        
        Mockito.doThrow(new UserAlreadyExistsException("Exists")).when(mockCompanyDAO).add(Matchers.any());
        
        // verify that we get an exception
        eventProcessor.processEvent(event);
    }
    
    @Test(expected = UserAlreadyExistsException.class)
    public void testProcessEventWhenSubscriptionExists() throws Exception {
        CreateSubscriptionEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));
        
        Mockito.doThrow(new UserAlreadyExistsException("Exists")).when(mockCompanyDAO).add(Matchers.any());
        
        // verify that we get an exception
        eventProcessor.processEvent(event);
    }
    
}
