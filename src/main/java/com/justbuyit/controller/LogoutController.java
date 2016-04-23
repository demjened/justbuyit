package com.justbuyit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.justbuyit.dao.UserDAO;
import com.justbuyit.entity.User;
import com.justbuyit.exception.UserNotFoundException;

/**
 * Controller for logging out of the application.
 */
@Controller
@RequestMapping("/logout")
public class LogoutController {

    private final static Logger LOG = LoggerFactory.getLogger(LogoutController.class);

    @Autowired
    private UserDAO userDAO;

    /**
     * Logs out the user from the application.
     * 
     * @param req
     *            the request
     * @param resp
     *            the response
     * @return the next model-and-view
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public String logout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String openId = (String) req.getSession().getAttribute("current_user_openid");

        LOG.info("/logout :: {}", openId);

        // fetch user
        User user = userDAO.findByOpenId(openId);
        if (user == null) {
            throw new UserNotFoundException(String.format("Cannot find user with OpenID [%s]", openId));
        }

        // remove authenticated flag and persist user
        user.setAuthenticated(false);
        userDAO.update(user);

        // navigate to logged out page
        return "loggedout";
    }

}
