package com.justbuyit.dao;

import java.util.List;

import com.justbuyit.entity.Company;
import com.justbuyit.exception.JustBuyItException;

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
    public abstract String create(Company company) throws JustBuyItException;

    /**
     * Updates the given company.
     * 
     * @param company
     *            the company
     * @throws JustBuyItException
     *             if there was an error during the action
     */
    public abstract Company update(Company company) throws JustBuyItException;

    /**
     * Deletes the given company.
     * 
     * @param companyId
     *            the company ID
     * @throws JustBuyItException
     *             if there was an error during the action
     */
    public abstract void delete(Company company) throws JustBuyItException;

    /**
     * Finds a company with the given ID.
     * @param companyId
     *            the company ID
     * @return the company
     */
    public abstract Company findById(String companyId);

    /**
     * Finds all companies.
     * 
     * @return the companies
     */
    public abstract List<Company> findAll();

}
