package com.justbuyit.eventprocessor.user;

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
import com.justbuyit.entity.User;
import com.justbuyit.model.event.user.AssignUserEvent;
import com.justbuyit.model.result.Result;
import com.justbuyit.util.TestUtils;

/**
 * Unit tests for {@link AssignUserEventProcessor}.
 */
@RunWith(MockitoJUnitRunner.class)
public class AssignUserEventProcessorTest {

    private final static String SAMPLE_FILE = "com/justbuyit/eventprocessor/user/assignUser.xml";

    @Mock
    ConnectionSigner mockConnectionSigner;

    @Mock
    CompanyDAO mockCompanyDAO;

    @InjectMocks
    private AssignUserEventProcessor eventProcessor = new AssignUserEventProcessor(mockConnectionSigner, mockCompanyDAO);

    /**
     * Tests unmarshalling of the sample event file.
     * 
     * @throws Exception
     */
    @Test
    public void testUnmarshal() throws Exception {
        Assert.assertEquals(AssignUserEvent.class, eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE)).getClass());
    }

    /**
     * Tests user assignment processing.
     * 
     * @throws Exception
     */
    @Test
    public void testProcessEvent() throws Exception {
        AssignUserEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));

        Company company = new Company();
        company.setUuid(event.getPayload().getAccount().getAccountIdentifier());
        Mockito.when(mockCompanyDAO.findById(event.getPayload().getAccount().getAccountIdentifier())).thenReturn(company);

        User user = new User();
        user.setUuid(event.getPayload().getUser().getUuid());

        Result result = eventProcessor.processEvent(event);

        // verify that the user gets assigned
        Assert.assertTrue(company.getUsers().contains(user));
        Mockito.verify(mockCompanyDAO).update(company);

        Assert.assertTrue(result.isSuccess());
    }

}
