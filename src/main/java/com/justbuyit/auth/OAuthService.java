package com.justbuyit.auth;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.justbuyit.exception.UnauthorizedException;

import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.SimpleOAuthValidator;
import net.oauth.server.OAuthServlet;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

/**
 * OAuth-based authorization service.
 */
public class OAuthService {

    private final static Logger LOG = LoggerFactory.getLogger(OAuthService.class);

    private DefaultOAuthConsumer signingConsumer;
    private OAuthAccessor accessor;
    private SimpleOAuthValidator validator = new SimpleOAuthValidator();

    /**
     * Creates an auth service with the given consumer key/secret.
     * 
     * @param consumerKey
     *            the consumer key
     * @param consumerSecret
     *            the consumer secret
     */
    public OAuthService(String consumerKey, String consumerSecret) {
        this.signingConsumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);

        OAuthConsumer validatingConsumer = new OAuthConsumer(null, consumerKey, consumerSecret, null);
        this.accessor = new OAuthAccessor(validatingConsumer);
    }

    /**
     * Opens a HTTP connection to the given URL, signs it with the app's OAuth credentials, then connects to it.
     * 
     * @param urlStr
     *            the URL string
     * @return the signed connection
     * @throws IOException
     * @throws OAuthCommunicationException
     * @throws OAuthExpectationFailedException
     * @throws OAuthMessageSignerException
     * @throws OAuthException
     */
    public HttpURLConnection openSignedConnection(String urlStr)
            throws IOException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        signingConsumer.sign(conn);
        conn.connect();
        return conn;
    }

    /**
     * Validates the given OAuth-signed HTTP request.
     * 
     * @param request
     *            the request
     * @throws UnauthorizedException
     *             if the validation failed
     */
    public void validate(HttpServletRequest request) throws UnauthorizedException {
        try {
            LOG.debug("Validating request URL [{}]", request.getRequestURL().toString());
            OAuthMessage oauthMessage = OAuthServlet.getMessage(request, null);
            validator.validateMessage(oauthMessage, accessor);
        } catch (OAuthException | URISyntaxException | IOException e) {
            LOG.error("Validation failed: [{}]", e.getMessage());
            throw new UnauthorizedException(e.getMessage());
        }
    }

}
