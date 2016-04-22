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
import com.justbuyit.model.event.user.UnassignUserEvent;
import com.justbuyit.model.result.Result;
import com.justbuyit.util.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class UnassignUserEventProcessorTest {

    private final static String SAMPLE_FILE = "com/justbuyit/eventprocessor/user/unassignUser.xml";
    
    @Mock
    ConnectionSigner mockConnectionSigner;
    
    @Mock
    UserDAO mockUserDAO;
    
    @InjectMocks
    private UnassignUserEventProcessor eventProcessor = new UnassignUserEventProcessor(mockConnectionSigner, mockUserDAO);

    @Test
    public void testUnmarshal() throws Exception {
        Assert.assertEquals(UnassignUserEvent.class, eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE)).getClass());
    }
    
    @Test
    public void testProcessEvent() throws Exception {
        UnassignUserEvent event = eventProcessor.unmarshalEvent(TestUtils.getSampleFileStream(SAMPLE_FILE));
        Result result = eventProcessor.processEvent(event);
        
        // verify that the user gets unassigned
        Mockito.verify(mockUserDAO).unassign(event.getPayload().getUser(), event.getPayload().getAccount().getAccountIdentifier());
        
        Assert.assertTrue(result.isSuccess());
    }
    
}
