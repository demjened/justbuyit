package com.justbuyit.dao;

import java.util.List;

import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.Company;

/**
 * DAO for managing {@link Company} entities (aka. customers).
 */
public interface CompanyDAO {

    /**
     * Adds a new company (aka. customer).
     * 
     * @param company
     *            the company
     * @return the ID of the company
     * @throws JustBuyItException
     *             if there was an error during the action
     */
    public abstract String add(Company company) throws JustBuyItException;

    /**
     * Deletes the given company.
     * 
     * @param companyId
     *            the company ID
     * @throws JustBuyItException
     *             if there was an error during the action
     */
    public abstract void delete(String companyId) throws JustBuyItException;

    /**
     * Finds all companies.
     * 
     * @return the companies
     */
    public abstract List<Company> findAll();

    /**
     * Updates the subscription status of the given company.
     * 
     * @param companyId
     *            the company ID
     * @param subscriptionStatus
     *            the new subscription status
     * @throws JustBuyItException
     *             if there was an error during the action
     */
    public abstract void updateSubscriptionStatus(String companyId, String subscriptionStatus) throws JustBuyItException;

}
