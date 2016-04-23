package com.justbuyit.eventprocessor.subscription;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.justbuyit.auth.OAuthService;
import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.entity.Company;
import com.justbuyit.model.event.subscription.NotifySubscriptionEvent;
import com.justbuyit.model.result.Result;
import com.justbuyit.util.TestUtils;

/**
 * Unit tests for {@link NotifySubscriptionEventProcessor}.
 */
@RunWith(MockitoJUnitRunner.class)
public class NotifySubscriptionEventProcessorTest {

    private final static String SAMPLE_FILE = "com/justbuyit/eventprocessor/subscription/notifySubscription.xml";
    
    @Mock
    OAuthService mockOAuthService;
    
    @Mock
    CompanyDAO mockCompanyDAO;
    
    @InjectMocks
    private NotifySubscriptionEventProcessor eventProcessor = new NotifySubscriptionEventProcessor(mockOAuthService, mockCompanyDAO);

    /**
     * Tests unmarshalling of the sample event file.
     * 
     * @throws Exception
     */
    @Test
    public void testUnmarshal() throws Exception {
        Assert.assertEquals(NotifySubscriptionEvent.class, eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE)).getClass());
    }
    
    /**
     * Tests subscription status notification processing.
     * 
     * @throws Exception
     */
    @Test
    public void testProcessEventClosed() throws Exception {
        NotifySubscriptionEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));
        event.getPayload().getNotice().setType("CLOSED");
        
        Company company = new Company();
        company.setUuid(event.getPayload().getAccount().getAccountIdentifier());
        Mockito.when(mockCompanyDAO.findById(event.getPayload().getAccount().getAccountIdentifier())).thenReturn(company);
        
        Result result = eventProcessor.processEvent(event);
        
        // verify that the subscription/company gets deleted 
        Mockito.verify(mockCompanyDAO).delete(company);
        
        Assert.assertTrue(result.isSuccess());
    }
    
    @Test
    public void testProcessEventDeactivated() throws Exception {
        NotifySubscriptionEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));
        event.getPayload().getNotice().setType("REACTIVATED");
        
        Company company = new Company();
        company.setUuid(event.getPayload().getAccount().getAccountIdentifier());
        Mockito.when(mockCompanyDAO.findById(event.getPayload().getAccount().getAccountIdentifier())).thenReturn(company);
        
        Result result = eventProcessor.processEvent(event);

        // verify that the subscription status gets updated 
        Assert.assertEquals(event.getPayload().getAccount().getStatus(), company.getSubscriptionStatus());
        Mockito.verify(mockCompanyDAO).update(company);
        
        Assert.assertTrue(result.isSuccess());
    }
    
    @Test
    public void testProcessEventUpcomingInvoice() throws Exception {
        NotifySubscriptionEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));
        event.getPayload().getNotice().setType("UPCOMING_INVOICE");
        Result result = eventProcessor.processEvent(event);
        
        // verify that the status does not get updated
        //Mockito.verify(mockCompanyDAO, Mockito.never()).updateSubscriptionStatus(Matchers.eq(event.getPayload().getAccount().getAccountIdentifier()), Matchers.anyString());
        
        Assert.assertTrue(result.isSuccess());
    }
    
}
