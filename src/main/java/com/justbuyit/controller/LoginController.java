package com.justbuyit.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.ax.FetchRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/login")
public class LoginController {
    
    public ConsumerManager manager = new ConsumerManager();

    @RequestMapping(method = RequestMethod.GET)
    public void login(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
        //String userOpenId = httpRequest.getParameter("openid");
        String userOpenId = "http://demjenedtest.wordpress.com";
        if("a".equals(userOpenId)) {
            //servletContext.getRequestDispatcher(ServiceUtils.getRelativeWelcomeEndpoint(httpRequest)).forward(httpRequest, httpResponse);
            System.out.println("a");
        } else {
            authRequest(userOpenId, "http://www.google.com", httpRequest, httpResponse);
        }
    }
    
    public String authRequest(String userSuppliedString, String returnToUrl, HttpServletRequest httpReq, HttpServletResponse httpResp) {
        System.out.println("authrequest: " + userSuppliedString + ", " + returnToUrl);
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
            fetch.addAttribute("email",                         // attribute alias
                    "http://schema.openid.net/contact/email",   // type URI
                    true);                                      // required

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
            // present error to the user
            e.printStackTrace();
        }
        return null;
    }

}
