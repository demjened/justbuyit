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
import com.justbuyit.model.event.subscription.ChangeSubscriptionEvent;
import com.justbuyit.model.result.Result;
import com.justbuyit.util.TestUtils;

/**
 * Unit tests for {@link ChangeSubscriptionEventProcessor}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ChangeSubscriptionEventProcessorTest {

    private final static String SAMPLE_FILE = "com/justbuyit/eventprocessor/subscription/changeSubscription.xml";
    
    @Mock
    OAuthService mockOAuthService;
    
    @Mock
    CompanyDAO mockCompanyDAO;
    
    @InjectMocks
    private ChangeSubscriptionEventProcessor eventProcessor = new ChangeSubscriptionEventProcessor(mockOAuthService, mockCompanyDAO);

    /**
     * Tests unmarshalling of the sample event file.
     * 
     * @throws Exception
     */
    @Test
    public void testUnmarshal() throws Exception {
        Assert.assertEquals(ChangeSubscriptionEvent.class, eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE)).getClass());
    }
    
    /**
     * Tests subscription change processing.
     * 
     * @throws Exception
     */
    @Test
    public void testProcessEvent() throws Exception {
        ChangeSubscriptionEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));
        
        Company company = new Company();
        company.setUuid(event.getPayload().getAccount().getAccountIdentifier());
        Mockito.when(mockCompanyDAO.findById(event.getPayload().getAccount().getAccountIdentifier())).thenReturn(company);

        Result result = eventProcessor.processEvent(event);
        
        // verify that the subscription gets updated
        Assert.assertEquals(event.getPayload().getOrder().getEditionCode(), company.getSubscriptionEditionCode());
        Mockito.verify(mockCompanyDAO).update(company);

        Assert.assertTrue(result.isSuccess());
    }
    
}
