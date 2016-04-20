package com.justbuyit.eventprocessor;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.justbuyit.auth.ConnectionSigner;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.event.Event;
import com.justbuyit.model.result.Result;

public abstract class EventProcessor<E extends Event<?>> {

    private final static Logger LOG = LoggerFactory.getLogger(EventProcessor.class);
    
    private ConnectionSigner connectionSigner;
    
    public EventProcessor(ConnectionSigner connectionSigner) {
        this.connectionSigner = connectionSigner;
    }

    public Result process(String eventUrl) throws Exception {
        // sign URL and connect
        URL url = new URL(eventUrl);
        HttpURLConnection conn = connectionSigner.openSignedConnection(url);
        
        // unmarshal response to specific event class, then process it
        try {
            InputStream is = conn.getInputStream();
            E event = unmarshalEvent(is);
            LOG.debug("Unmarshalled stream to event: [{}]", event);
            
            return processEvent(event);
        } catch (JustBuyItException e) {
            LOG.error("Error while processing event", e);
            return Result.errorResult(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            LOG.error("Unknown error while processing event", e);
            return Result.errorResult("UNKNOWN_ERROR", e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    E unmarshalEvent(InputStream is) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(getClassesToBeBound());
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        E event = (E) unmarshaller.unmarshal(is);
        return event;
    }
    
    protected abstract Result processEvent(E event) throws JustBuyItException;
    
    protected abstract Class<?>[] getClassesToBeBound();
    
}
