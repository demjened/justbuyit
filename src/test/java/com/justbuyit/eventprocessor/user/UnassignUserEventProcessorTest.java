package com.justbuyit.eventprocessor.user;

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
import com.justbuyit.entity.User;
import com.justbuyit.model.event.user.UnassignUserEvent;
import com.justbuyit.model.result.Result;
import com.justbuyit.util.TestUtils;

/**
 * Unit tests for {@link UnassignUserEventProcessor}.
 */
@RunWith(MockitoJUnitRunner.class)
public class UnassignUserEventProcessorTest {

    private final static String SAMPLE_FILE = "com/justbuyit/eventprocessor/user/unassignUser.xml";
    
    @Mock
    OAuthService mockOAuthService;
    
    @Mock
    CompanyDAO mockCompanyDAO;
    
    @InjectMocks
    private UnassignUserEventProcessor eventProcessor = new UnassignUserEventProcessor(mockOAuthService, mockCompanyDAO);

    /**
     * Tests unmarshalling of the sample event file.
     * 
     * @throws Exception
     */
    @Test
    public void testUnmarshal() throws Exception {
        Assert.assertEquals(UnassignUserEvent.class, eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE)).getClass());
    }
    
    /**
     * Tests user unassignment processing.
     * 
     * @throws Exception
     */
    @Test
    public void testProcessEvent() throws Exception {
        UnassignUserEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));
        
        Company company = new Company();
        company.setUuid(event.getPayload().getAccount().getAccountIdentifier());
        Mockito.when(mockCompanyDAO.findById(event.getPayload().getAccount().getAccountIdentifier())).thenReturn(company);
        
        User user = new User();
        user.setUuid(event.getPayload().getUser().getUuid());
        user.setCompany(company);
        company.getUsers().add(user);
        
        Result result = eventProcessor.processEvent(event);
        
        // verify that the user gets unassigned
        Assert.assertFalse(company.getUsers().contains(user));
        Mockito.verify(mockCompanyDAO).update(company);
        
        Assert.assertTrue(result.isSuccess());
    }
    
}
