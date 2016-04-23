package com.justbuyit.eventprocessor;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.justbuyit.auth.OAuthService;
import com.justbuyit.eventprocessor.subscription.CancelSubscriptionEventProcessor;
import com.justbuyit.exception.AccountNotFoundException;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.subscription.CancelSubscriptionEvent;
import com.justbuyit.model.result.Result;

/**
 * Unit tests for {@link EventProcessor}.
 */
@RunWith(MockitoJUnitRunner.class)
public class EventProcessorTest {

    @Mock
    OAuthService mockOAuthService;
    
    @Mock
    HttpURLConnection conn;
    
    @InjectMocks
    private CancelSubscriptionEventProcessor accountNotFoundThrowingEventProcessor = new CancelSubscriptionEventProcessor(mockOAuthService, null) {
        @Override
        protected Result processEvent(CancelSubscriptionEvent event) throws JustBuyItException {
            throw new AccountNotFoundException("Error");
        }
        
        @Override
        public CancelSubscriptionEvent unmarshalEvent(InputStream is) throws JAXBException, IOException {
            return null;
        }
    };
    
    @InjectMocks
    private CancelSubscriptionEventProcessor runtimeExceptionThrowingEventProcessor = new CancelSubscriptionEventProcessor(mockOAuthService, null) {
        @Override
        protected Result processEvent(CancelSubscriptionEvent event) throws JustBuyItException {
            throw new RuntimeException("Runtime Error");
        }
        
        @Override
        public CancelSubscriptionEvent unmarshalEvent(InputStream is) throws JAXBException, IOException {
            return null;
        }
    };
    
    @Test
    public void testProcessWithJustBuyItException() throws Exception {
        Mockito.when(mockOAuthService.openSignedConnection(Matchers.anyObject())).thenReturn(conn);
        
        Result result = accountNotFoundThrowingEventProcessor.process(null, "http://url");
        Assert.assertFalse(result.isSuccess());
        Assert.assertEquals("ACCOUNT_NOT_FOUND", result.getErrorCode());
        
        Mockito.verify(mockOAuthService).openSignedConnection("http://url");
    }
    
    @Test
    public void testProcessWithRuntimeException() throws Exception {
        Mockito.when(mockOAuthService.openSignedConnection(Matchers.anyObject())).thenReturn(conn);
        
        Result result = runtimeExceptionThrowingEventProcessor.process(null, "http://url");
        Assert.assertFalse(result.isSuccess());
        Assert.assertEquals("UNKNOWN_ERROR", result.getErrorCode());
    }
    
}
