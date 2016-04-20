package com.justbuyit.dao;

import org.springframework.stereotype.Repository;

import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.Order;

@Repository
public interface SubscriptionDAO {

    public abstract void create(String id, Order order) throws JustBuyItException;
    
    public abstract void cancel(String id) throws JustBuyItException;
    
    public abstract void update(String id, Order order) throws JustBuyItException;
    
    public abstract Order find(String id) throws JustBuyItException;
    
}
