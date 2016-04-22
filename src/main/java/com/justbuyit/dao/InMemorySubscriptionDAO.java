package com.justbuyit.dao;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.google.inject.internal.Maps;
import com.justbuyit.exception.AccountNotFoundException;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.exception.UserAlreadyExistsException;
import com.justbuyit.model.Order;

public class InMemorySubscriptionDAO implements SubscriptionDAO {

    private final static Logger LOG = LoggerFactory.getLogger(InMemorySubscriptionDAO.class);

    private final Map<String, Order> subscriptions = Maps.newHashMap();
    
    @Override
    public void add(String companyId, Order order) throws JustBuyItException {
        Assert.notNull(companyId);
        Assert.notNull(order);
        
        LOG.info("Creating subscription for company ID [{}]", companyId);
        
        // check if the company already has a subscription
        if (subscriptions.containsKey(companyId)) {
            throw new UserAlreadyExistsException(String.format("Company with ID [%s] is already subscribed", companyId));
        }
        
        subscriptions.put(companyId, order);
    }

    @Override
    public void delete(String companyId) throws JustBuyItException {
        Assert.notNull(companyId);
        
        LOG.info("Removing subscription for company ID [{}]", companyId);
        
        // check if a subscription exists for the company
        checkSubscriptionExists(companyId);

        subscriptions.remove(companyId);
    }

    @Override
    public void update(String companyId, Order order) throws JustBuyItException {
        Assert.notNull(companyId);
        Assert.notNull(order);

        LOG.info("Updating subscription for company ID [{}]", companyId);
        
        // check if a subscription exists for the company
        checkSubscriptionExists(companyId);

        subscriptions.put(companyId, order);
    }

    /**
     * Checks if a subscription exists for the given company.
     * 
     * @param companyId
     *            the company ID
     * @throws AccountNotFoundException
     *             if the company/subscription does not exist
     */
    private void checkSubscriptionExists(String companyId) throws AccountNotFoundException {
        if (!subscriptions.containsKey(companyId)) {
            throw new AccountNotFoundException(String.format("Company with ID [%s] has no subscriptions", companyId));
        }
    }

}
