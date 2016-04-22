package com.justbuyit.auth;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthException;

@Component
public class ConnectionSigner {
    
    private OAuthConsumer consumer;
    
    public ConnectionSigner(String consumerKey, String consumerSecret) {
        consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
    }
    
    public HttpURLConnection openSignedConnection(URL url) throws IOException, OAuthException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        consumer.sign(conn);
        conn.connect();
        return conn;
    }
    
}
