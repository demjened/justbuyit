package com.justbuyit.auth;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.springframework.stereotype.Component;

/**
 * OpenID-based authenticator.
 */
@Component
@SuppressWarnings("rawtypes")
public class OpenIdAuthenticator {

    public ConsumerManager manager = new ConsumerManager();

    // --- placing the authentication request ---
    public String authRequest(String userSuppliedString, String returnToUrl, HttpServletRequest httpReq, HttpServletResponse httpResp) {
        try {
            // perform discovery on the user-supplied identifier
            List discoveries = manager.discover(userSuppliedString);

            // attempt to associate with the OpenID provider
            // and retrieve one service endpoint for authentication
            DiscoveryInformation discovered = manager.associate(discoveries);

            // store the discovery information in the user's session
            httpReq.getSession().setAttribute("openid-disc", discovered);

            // obtain a AuthRequest message to be sent to the OpenID provider
            AuthRequest authReq = manager.authenticate(discovered, returnToUrl);

            // Attribute Exchange example: fetching the 'email' attribute
            FetchRequest fetch = FetchRequest.createFetchRequest();
            fetch.addAttribute("email", // attribute alias
                    "http://schema.openid.net/contact/email", // type URI
                    true); // required

            // attach the extension to the authentication request
            authReq.addExtension(fetch);

            // Option 1: GET HTTP-redirect to the OpenID Provider endpoint
            // The only method supported in OpenID 1.x
            // redirect-URL usually limited ~2048 bytes
            try {
                httpResp.sendRedirect(authReq.getDestinationUrl(true));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        } catch (OpenIDException e) {
            e.printStackTrace(); // TODO: relay the info back to the user
        }

        return null;
    }

    // --- processing the authentication response ---
    public Identifier verifyResponse(HttpServletRequest httpReq) {
        try {
            // extract the parameters from the authentication response
            // (which comes in as a HTTP request from the OpenID provider)
            ParameterList response = new ParameterList(httpReq.getParameterMap());

            // retrieve the previously stored discovery information
            DiscoveryInformation discovered = (DiscoveryInformation) httpReq.getSession().getAttribute("openid-disc");

            // extract the receiving URL from the HTTP request
            StringBuffer receivingURL = httpReq.getRequestURL();
            String queryString = httpReq.getQueryString();
            if (queryString != null && queryString.length() > 0)
                receivingURL.append("?").append(httpReq.getQueryString());

            // verify the response; ConsumerManager needs to be the same
            // (static) instance used to place the authentication request
            VerificationResult verification = manager.verify(receivingURL.toString(), response, discovered);

            // examine the verification result and extract the verified identifier
            Identifier verified = verification.getVerifiedId();
            if (verified != null) {
                AuthSuccess authSuccess = (AuthSuccess) verification.getAuthResponse();

                if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
                    FetchResponse fetchResp = (FetchResponse) authSuccess.getExtension(AxMessage.OPENID_NS_AX);

                    List emails = fetchResp.getAttributeValues("email");
                    emails.get(0);
                    //String email = (String) emails.get(0);
                }

                return verified; // success
            }
        } catch (OpenIDException e) {
            e.printStackTrace(); // TODO: relay the info back to the user
        }

        return null;
    }

}
