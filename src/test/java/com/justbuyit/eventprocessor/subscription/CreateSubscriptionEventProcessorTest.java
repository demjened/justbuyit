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
import com.justbuyit.entity.Company;
import com.justbuyit.exception.UserAlreadyExistsException;
import com.justbuyit.model.event.subscription.CreateSubscriptionEvent;
import com.justbuyit.model.result.Result;
import com.justbuyit.util.TestUtils;

/**
 * Unit tests for {@link CreateSubscriptionEventProcessor}.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateSubscriptionEventProcessorTest {
    
    private final static String SAMPLE_FILE = "com/justbuyit/eventprocessor/subscription/createSubscription.xml";

    @Mock
    ConnectionSigner mockConnectionSigner;
    
    @Mock
    CompanyDAO mockCompanyDAO;
    
    @InjectMocks
    private CreateSubscriptionEventProcessor eventProcessor = new CreateSubscriptionEventProcessor(mockConnectionSigner, mockCompanyDAO);

    /**
     * Tests unmarshalling of the sample event file.
     * 
     * @throws Exception
     */
    @Test
    public void testUnmarshal() throws Exception {
        Assert.assertEquals(CreateSubscriptionEvent.class, eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE)).getClass());
    }
    
    /**
     * Tests subscription creation processing.
     * 
     * @throws Exception
     */
    @Test
    public void testProcessEvent() throws Exception {
        CreateSubscriptionEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));

        Company company = new Company(event.getPayload().getCompany());
        Mockito.when(mockCompanyDAO.create(Matchers.anyObject())).thenReturn(company);
        Mockito.when(mockCompanyDAO.findById("123")).thenReturn(company);
        
        Result result = eventProcessor.processEvent(event);
        
        // verify that the entities get created
        Mockito.verify(mockCompanyDAO).create(new Company(event.getPayload().getCompany()));
        
        Assert.assertTrue(result.isSuccess());
    }
    
    @Test(expected = UserAlreadyExistsException.class)
    public void testProcessEventWhenCompanyExists() throws Exception {
        CreateSubscriptionEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));
        
        Company company = new Company(event.getPayload().getCompany());
        Mockito.when(mockCompanyDAO.findById(Matchers.anyString())).thenReturn(company);
        
        // verify that we get an exception
        eventProcessor.processEvent(event);
    }
    
}
