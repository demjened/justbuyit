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
import com.justbuyit.model.event.subscription.NotifySubscriptionEvent;
import com.justbuyit.model.result.Result;
import com.justbuyit.util.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class NotifySubscriptionEventProcessorTest {

    private final static String SAMPLE_FILE = "com/justbuyit/eventprocessor/subscription/notifySubscription.xml";
    
    @Mock
    ConnectionSigner mockConnectionSigner;
    
    @Mock
    CompanyDAO mockCompanyDAO;
    
    @Mock
    SubscriptionDAO mockSubscriptionDAO;

    @Mock
    UserDAO mockUserDAO;
    
    @InjectMocks
    private NotifySubscriptionEventProcessor eventProcessor = new NotifySubscriptionEventProcessor(mockConnectionSigner, mockCompanyDAO, mockSubscriptionDAO, mockUserDAO);

    @Test
    public void testUnmarshal() throws Exception {
        Assert.assertEquals(NotifySubscriptionEvent.class, eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE)).getClass());
    }
    
    @Test
    public void testProcessEventClosed() throws Exception {
        NotifySubscriptionEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));
        event.getPayload().getNotice().setType("CLOSED");
        Result result = eventProcessor.processEvent(event);
        
        // verify that the entities get deleted
        Mockito.verify(mockSubscriptionDAO).delete(event.getPayload().getAccount().getAccountIdentifier());
        Mockito.verify(mockCompanyDAO).delete(event.getPayload().getAccount().getAccountIdentifier());
        Mockito.verify(mockUserDAO).deleteAll(event.getPayload().getAccount().getAccountIdentifier());
        
        Assert.assertTrue(result.isSuccess());
    }
    
    @Test
    public void testProcessEventDeactivated() throws Exception {
        NotifySubscriptionEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));
        event.getPayload().getNotice().setType("REACTIVATED");
        Result result = eventProcessor.processEvent(event);
        
        // verify that the status gets updated
        Mockito.verify(mockCompanyDAO).updateSubscriptionStatus(event.getPayload().getAccount().getAccountIdentifier(), event.getPayload().getAccount().getStatus());
        
        Assert.assertTrue(result.isSuccess());
    }
    
    @Test
    public void testProcessEventUpcomingInvoice() throws Exception {
        NotifySubscriptionEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));
        event.getPayload().getNotice().setType("UPCOMING_INVOICE");
        Result result = eventProcessor.processEvent(event);
        
        // verify that the status does not get updated
        Mockito.verify(mockCompanyDAO, Mockito.never()).updateSubscriptionStatus(Matchers.eq(event.getPayload().getAccount().getAccountIdentifier()), Matchers.anyString());
        
        Assert.assertTrue(result.isSuccess());
    }
    
}
