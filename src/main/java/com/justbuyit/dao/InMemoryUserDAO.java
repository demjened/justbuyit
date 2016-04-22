package com.justbuyit.dao;

import java.util.Optional;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.justbuyit.exception.AccountNotFoundException;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.exception.UserNotFoundException;
import com.justbuyit.model.User;

public class InMemoryUserDAO implements UserDAO {

    private final MultiValuedMap<String, User> userAssignments = new ArrayListValuedHashMap<String, User>();
    
    private final static Logger LOG = LoggerFactory.getLogger(InMemoryUserDAO.class);
    
    @Override
    public void assign(User user, String companyId) throws JustBuyItException {
        Assert.notNull(user);
        Assert.notNull(companyId);

        LOG.info("Assigning user [{}] to company [{}]", user.getUuid(), companyId);
        
        userAssignments.put(companyId, user);
    }

    @Override
    public void unassign(User user, String companyId) throws JustBuyItException {
        Assert.notNull(user);
        Assert.notNull(companyId);

        LOG.info("Unassigning user [{}] from company [{}]", user.getUuid(), companyId);

        // check if the company exists
        checkCompanyExists(companyId);
        
        userAssignments.removeMapping(companyId, user);
    }

    @Override
    public void deleteAll(String companyId) throws JustBuyItException {
        LOG.info("Removing all assignments for company [{}]", companyId);

        // check if the company exists
        checkCompanyExists(companyId);
        
        userAssignments.remove(companyId);
    }

    @Override
    public boolean isAuthenticated(String openId) {
        LOG.info("Checking if user with OpenID [{}] is authenticated", openId);
        
        Optional<User> result = userAssignments.values().stream()
                                                        .filter(u -> u.getOpenId().equals(openId))
                                                        .findFirst();
        return result.isPresent() && result.get().isAuthenticated();
    }

    @Override
    public void setAuthenticated(String openId, boolean authenticated) throws JustBuyItException {
        LOG.info("Setting user with OpenID [{}] to authenticated: [{}]", openId, authenticated);
        
        Optional<User> result = userAssignments.values().stream()
                                                        .filter(u -> u.getOpenId().equals(openId))
                                                        .findFirst();
        
        // check if the user exists
        if (!result.isPresent()) {
            throw new UserNotFoundException(String.format("User with OpenId [%s] does not exist", openId));
        }
        
        result.get().setAuthenticated(authenticated);        
    }
    
    /**
     * Checks if the given company exists.
     * 
     * @param companyId
     *            the company ID
     * @throws AccountNotFoundException
     *             if the company/subscription does not exist
     */
    private void checkCompanyExists(String companyId) throws AccountNotFoundException {
        if (!userAssignments.containsKey(companyId)) {
            throw new AccountNotFoundException(String.format("Company with ID [%s] has no subscriptions", companyId));
        }
    }
    
}
