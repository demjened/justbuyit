package com.justbuyit.eventprocessor.subscription;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.dao.SubscriptionDAO;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.model.event.subscription.CancelSubscriptionEvent;
import com.justbuyit.model.result.Result;
import com.justbuyit.util.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class CancelSubscriptionEventProcessorTest {

    private final static String SAMPLE_FILE = "com/justbuyit/eventprocessor/subscription/cancelSubscription.xml";
    
    @Mock
    ConnectionSigner mockConnectionSigner;
    
    @Mock
    CompanyDAO mockCompanyDAO;
    
    @Mock
    SubscriptionDAO mockSubscriptionDAO;
    
    @Mock
    UserDAO mockUserDAO;
    
    @InjectMocks
    private CancelSubscriptionEventProcessor eventProcessor = new CancelSubscriptionEventProcessor(mockConnectionSigner, mockCompanyDAO, mockSubscriptionDAO, mockUserDAO);

    @Test
    public void testUnmarshal() throws Exception {
        Assert.assertEquals(CancelSubscriptionEvent.class, eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE)).getClass());
    }
    
    @Test
    public void testProcessEvent() throws Exception {
        CancelSubscriptionEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));
        Result result = eventProcessor.processEvent(event);
        
        // verify that the entities get deleted
        Mockito.verify(mockSubscriptionDAO).delete(event.getPayload().getAccount().getAccountIdentifier());
        Mockito.verify(mockCompanyDAO).delete(event.getPayload().getAccount().getAccountIdentifier());
        Mockito.verify(mockUserDAO).deleteAll(event.getPayload().getAccount().getAccountIdentifier());
        
        Assert.assertTrue(result.isSuccess());
    }
    
}
