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
import com.justbuyit.entity.Company;
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
    
    @InjectMocks
    private CancelSubscriptionEventProcessor eventProcessor = new CancelSubscriptionEventProcessor(mockConnectionSigner, mockCompanyDAO);

    @Test
    public void testUnmarshal() throws Exception {
        Assert.assertEquals(CancelSubscriptionEvent.class, eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE)).getClass());
    }
    
    @Test
    public void testProcessEvent() throws Exception {
        CancelSubscriptionEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));
        
        Company company = new Company();
        company.setUuid(event.getPayload().getAccount().getAccountIdentifier());
        Mockito.when(mockCompanyDAO.findById(event.getPayload().getAccount().getAccountIdentifier())).thenReturn(company);
        
        Result result = eventProcessor.processEvent(event);
        
        // verify that the entities get deleted
        Mockito.verify(mockCompanyDAO).delete(company);
        
        Assert.assertTrue(result.isSuccess());
    }
    
}
