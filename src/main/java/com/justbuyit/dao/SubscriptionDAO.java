package com.justbuyit.dao;

import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.Order;

public interface SubscriptionDAO {

    public abstract void create(String id, Order order) throws JustBuyItException;
    
    public abstract void cancel(String id) throws JustBuyItException;
    
    public abstract void update(String id, Order order) throws JustBuyItException;
    
    public abstract Order find(String id) throws JustBuyItException;
    
}
