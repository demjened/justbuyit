package com.justbuyit.eventprocessor.user;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.model.event.user.AssignUserEvent;
import com.justbuyit.model.result.Result;
import com.justbuyit.util.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class AssignUserEventProcessorTest {

    private final static String SAMPLE_FILE = "com/justbuyit/eventprocessor/user/assignUser.xml";
    
    @Mock
    ConnectionSigner mockConnectionSigner;
    
    @Mock
    UserDAO mockUserDAO;
    
    @InjectMocks
    private AssignUserEventProcessor eventProcessor = new AssignUserEventProcessor(mockConnectionSigner, mockUserDAO);

    @Test
    public void testUnmarshal() throws Exception {
        Assert.assertEquals(AssignUserEvent.class, eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE)).getClass());
    }
    
    @Test
    public void testProcessEvent() throws Exception {
        AssignUserEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));
        Result result = eventProcessor.processEvent(event);
        
        // verify that the user gets assigned
        Mockito.verify(mockUserDAO).assign(event.getPayload().getUser(), event.getPayload().getAccount().getAccountIdentifier());
        
        Assert.assertTrue(result.isSuccess());
    }
    
}
