package com.justbuyit.eventprocessor.subscription;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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

@RunWith(MockitoJUnitRunner.class)
public class CreateSubscriptionEventProcessorTest {

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
        Assert.assertEquals(CreateSubscriptionEvent.class, eventProcessor.unmarshalEvent((getSampleFile())).getClass());
    }
    
    @Test
    public void testProcessEvent() throws Exception {
        CreateSubscriptionEvent event = eventProcessor.unmarshalEvent((getSampleFile()));
        Result result = eventProcessor.processEvent(event);
        
        Mockito.verify(mockCompanyDAO).add(event.getPayload().getCompany());
        
        Assert.assertTrue(result.isSuccess());
    }
    
    @Test(expected = UserAlreadyExistsException.class)
    public void testProcessEventWhenCompanyExists() throws Exception {
        CreateSubscriptionEvent event = eventProcessor.unmarshalEvent((getSampleFile()));
        
        Mockito.doThrow(new UserAlreadyExistsException("Exists")).when(mockCompanyDAO).add(Matchers.any());
        
        eventProcessor.processEvent(event);
    }
    
    private InputStream getSampleFile() throws FileNotFoundException {
        File resourcesDirectory = new File("src/test/resources");
        File eventXML = new File(resourcesDirectory, "com/justbuyit/eventprocessor/subscription/createSubscription.xml");
        return new FileInputStream(eventXML);
    }
    
}
