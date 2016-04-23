package com.justbuyit.auth;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthException;

/**
 * OAuth-based outgoing connection signer.
 */
public class ConnectionSigner {

    private OAuthConsumer consumer;

    /**
     * Creates a connection signer with the given consumer key/secret
     * 
     * @param consumerKey
     *            the consumer key
     * @param consumerSecret
     *            the consumer secret
     */
    public ConnectionSigner(String consumerKey, String consumerSecret) {
        consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
    }

    /**
     * Opens and signs a HTTP connection to the given URL.
     * 
     * @param url
     *            the URL
     * @return the connection
     * @throws IOException
     * @throws OAuthException
     */
    public HttpURLConnection openSignedConnection(URL url) throws IOException, OAuthException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        consumer.sign(conn);
        conn.connect();
        return conn;
    }

}
