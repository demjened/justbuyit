package com.justbuyit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.discovery.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.justbuyit.auth.OpenIdAuthenticator;
import com.justbuyit.dao.UserDAO;
import com.justbuyit.entity.User;

/**
 * Controller for logging in to the application and handling OpenID-based authentication.
 */
@Controller
@RequestMapping(value = "/login", method = RequestMethod.GET)
public class LoginController extends ExceptionHandlingController {

    private final static Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private OpenIdAuthenticator openIdAuthenticator;

    @Autowired
    private UserDAO userDAO;

    /**
     * Initiates the login procedure of a user.
     * 
     * @param req
     *            the request
     * @param resp
     *            the response
     * @return the next model-and-view
     */
    @RequestMapping
    public ModelAndView login(HttpServletRequest req, HttpServletResponse resp) {
        String openId = req.getParameter("openid_url");
        LOG.info("/login :: {}", openId);

        if (!StringUtils.isEmpty(openId)) {

            // fetch user by OpenID and check if they are authenticated
            User user = userDAO.findByOpenId(openId);
            if (user != null && user.isAuthenticated()) {
                LOG.debug("User with openId [{}] is already authenticated, forwarding to /", openId);
                return new ModelAndView("forward:/");
            } else {
                LOG.debug("User with openId [{}] has not been authenticated yet, redirecting to /openid", openId);
                openIdAuthenticator.authRequest(openId, req.getRequestURL().append("/openid").toString(), req, resp);
                return null;
            }
        }

        // navigate to forbidden page
        resp.setStatus(403);
        return forbidden("Please log in at AppDirect and launch the application from MyApps.");
    }

    /**
     * Authenticates the user via their OpenID.
     *   
     * @param req
     *            the request (containing OpenID attributes) 
     * @param resp
     *            the response
     * @return the next model-and-view
     * @throws Exception
     */
    @RequestMapping("/openid")
    public ModelAndView openid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        LOG.info("/login/openid");

        // verify OpenID authentication response
        String openId;
        Identifier identity = openIdAuthenticator.verifyResponse(req);
        if (identity != null) {

            // fetch user
            openId = identity.getIdentifier();
            User user = userDAO.findByOpenId(openId);
            if (user != null) {
                // set and persist authenticated flag for future logins
                user.setAuthenticated(true);
                req.getSession().setAttribute("current_user_openid", openId);
                userDAO.update(user);

                // navigate to main page
                LOG.debug("User with OpenID [{}] has been authenticated, forwarding to /", openId);
                return new ModelAndView("forward:/");
            }
        }

        // navigate to forbidden page
        resp.setStatus(403);
        return forbidden("You have not been assigned to this application. Please contact your administrator.");
    }

    /**
     * Generates a model-and-view to the forbidden page with the given message.
     * 
     * @param message
     *            the reason why the user cannot access the app
     * @return the next model-and-view
     */
    private ModelAndView forbidden(String message) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", message);
        modelAndView.setViewName("forbidden");
        return modelAndView;
    }

}
