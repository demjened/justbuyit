package com.justbuyit.eventprocessor.subscription;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.SubscriptionDAO;
import com.justbuyit.model.event.subscription.ChangeSubscriptionEvent;
import com.justbuyit.model.result.Result;
import com.justbuyit.util.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ChangeSubscriptionEventProcessorTest {

    private final static String SAMPLE_FILE = "com/justbuyit/eventprocessor/subscription/changeSubscription.xml";
    
    @Mock
    ConnectionSigner mockConnectionSigner;
    
    @Mock
    SubscriptionDAO mockSubscriptionDAO;
    
    @InjectMocks
    private ChangeSubscriptionEventProcessor eventProcessor = new ChangeSubscriptionEventProcessor(mockConnectionSigner, mockSubscriptionDAO);

    @Test
    public void testUnmarshal() throws Exception {
        Assert.assertEquals(ChangeSubscriptionEvent.class, eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE)).getClass());
    }
    
    @Test
    public void testProcessEvent() throws Exception {
        ChangeSubscriptionEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));
        Result result = eventProcessor.processEvent(event);
        
        // verify that the subscription gets updated
        Mockito.verify(mockSubscriptionDAO).update(event.getPayload().getAccount().getAccountIdentifier(), event.getPayload().getOrder());
        
        Assert.assertTrue(result.isSuccess());
    }
    
}
