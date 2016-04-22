package com.justbuyit.dao;

import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.Order;

public interface SubscriptionDAO {

    /**
     * Creates a subscription for the given company.
     * 
     * @param companyId
     *            the company ID
     * @param order
     *            the subscription order
     * @throws JustBuyItException
     *             if there was an error during the action
     */
    public abstract void add(String companyId, Order order) throws JustBuyItException;

    /**
     * Deletes the subscription of the given company.
     * 
     * @param companyId
     *            the company ID
     * @throws JustBuyItException
     *             if there was an error during the action
     */
    public abstract void delete(String companyId) throws JustBuyItException;

    /**
     * Updates the subscription for the given company.
     * 
     * @param companyId
     *            the company ID
     * @param order
     *            the new subscription order
     * @throws JustBuyItException
     *             if there was an error during the action
     */
    public abstract void update(String companyId, Order order) throws JustBuyItException;

    // public abstract Order find(String id) throws JustBuyItException;

}
