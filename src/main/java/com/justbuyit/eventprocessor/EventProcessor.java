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

/**
 * Base event processor that processes an event of a specific type.
 * 
 * @param <E>
 *            the event type
 */
public abstract class EventProcessor<E extends Event<?>> {

    private final static Logger LOG = LoggerFactory.getLogger(EventProcessor.class);

    private ConnectionSigner connectionSigner;

    public EventProcessor(ConnectionSigner connectionSigner) {
        this.connectionSigner = connectionSigner;
    }

    /**
     * Processes an event: 1. fetches the event XML from the given URL via signed connection, 2. unmarshals it into an event object, 3. executes event
     * type-specific processing.
     * 
     * @param eventUrl
     *            the event XML's URL
     * @return the result of the processing
     * @throws Exception
     *             if there was an error during processing
     */
    public Result process(String eventUrl) throws Exception {
        // sign URL and connect
        URL url = new URL(eventUrl);
        HttpURLConnection conn = connectionSigner.openSignedConnection(url);

        // unmarshal response to specific event class, then process it
        try {
            InputStream is = conn.getInputStream();
            E event = unmarshalEvent(is);

            LOG.debug("Processing event: [{}]", event);
            return processEvent(event);
        } catch (JustBuyItException e) { // domain exception
            LOG.error("Error while processing event", e);
            return Result.errorResult(e.getErrorCode(), e.getMessage());
        } catch (Exception e) { // any other exception
            LOG.error("Unknown error while processing event", e);
            return Result.errorResult("UNKNOWN_ERROR", e.getMessage());
        }
    }

    /**
     * Unmarshals the event object from the given input stream.
     * 
     * @param is
     *            the input stream
     * @return the unmarshalled object
     * @throws JAXBException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public E unmarshalEvent(InputStream is) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(getClassesToBeBound());
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        E event = (E) unmarshaller.unmarshal(is);
        return event;
    }

    /**
     * Processes the event object.
     * 
     * @param event
     *            the event
     * @return the result of the processing
     * @throws JustBuyItException
     *             if there was an error during processing
     */
    protected abstract Result processEvent(E event) throws JustBuyItException;

    /**
     * Gets the array of classes to be bound with the JAXB unmarshaller.
     * 
     * @return the classes
     */
    protected abstract Class<?>[] getClassesToBeBound();

}
