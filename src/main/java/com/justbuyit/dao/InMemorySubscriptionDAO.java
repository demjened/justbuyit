package com.justbuyit.dao;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.google.inject.internal.Maps;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.exception.SubscriptionAlreadyExistsException;
import com.justbuyit.exception.SubscriptionNotExistsException;
import com.justbuyit.model.Order;

public class InMemorySubscriptionDAO implements SubscriptionDAO {

    private final static Logger LOG = LoggerFactory.getLogger(InMemorySubscriptionDAO.class);

    private final Map<String, Order> subscriptions = Maps.newHashMap();
    
    @Override
    public void create(String id, Order order) throws JustBuyItException {
        Assert.notNull(id);
        Assert.notNull(order);
        
        if (subscriptions.containsKey(id)) {
            throw new SubscriptionAlreadyExistsException(String.format("Company with ID [%s] is already subscribed", id));
        }
        
        LOG.info("...");
        subscriptions.put(id, order);
    }

    @Override
    public void cancel(String id) throws JustBuyItException {
        Assert.notNull(id);
        checkSubscriptionExists(id);

        subscriptions.remove(id);
    }

    @Override
    public void update(String id, Order order) throws JustBuyItException {
        Assert.notNull(id);
        Assert.notNull(order);
        checkSubscriptionExists(id);

        subscriptions.put(id, order);
    }

    @Override
    public Order find(String id) throws JustBuyItException {
        Assert.notNull(id);
        
        return subscriptions.get(id);
    }
    
    private void checkSubscriptionExists(String id) throws SubscriptionNotExistsException {
        if (!subscriptions.containsKey(id)) {
            throw new SubscriptionNotExistsException(String.format("Company with ID [%s] has no subscriptions", id));
        }
    }

}
