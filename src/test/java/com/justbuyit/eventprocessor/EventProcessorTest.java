package com.justbuyit.eventprocessor;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.eventprocessor.subscription.CancelSubscriptionEventProcessor;
import com.justbuyit.exception.AccountNotFoundException;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.subscription.CancelSubscriptionEvent;
import com.justbuyit.model.result.Result;

@RunWith(MockitoJUnitRunner.class)
public class EventProcessorTest {

    @Mock
    ConnectionSigner mockConnectionSigner;
    
    @Mock
    HttpURLConnection conn;
    
    @InjectMocks
    private CancelSubscriptionEventProcessor accountNotFoundThrowingEventProcessor = new CancelSubscriptionEventProcessor(mockConnectionSigner, null, null, null) {
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
    private CancelSubscriptionEventProcessor runtimeExceptionThrowingEventProcessor = new CancelSubscriptionEventProcessor(mockConnectionSigner, null, null, null) {
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
        Mockito.when(mockConnectionSigner.openSignedConnection(Matchers.anyObject())).thenReturn(conn);
        
        Result result = accountNotFoundThrowingEventProcessor.process("http://url");
        Assert.assertFalse(result.isSuccess());
        Assert.assertEquals("ACCOUNT_NOT_FOUND", result.getErrorCode());
        
        Mockito.verify(mockConnectionSigner).openSignedConnection(new URL("http://url"));
    }
    
    @Test
    public void testProcessWithRuntimeException() throws Exception {
        Mockito.when(mockConnectionSigner.openSignedConnection(Matchers.anyObject())).thenReturn(conn);
        
        Result result = runtimeExceptionThrowingEventProcessor.process("http://url");
        Assert.assertFalse(result.isSuccess());
        Assert.assertEquals("UNKNOWN_ERROR", result.getErrorCode());
    }
    
}
