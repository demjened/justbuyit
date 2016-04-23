package com.justbuyit.dao;

import java.util.List;

import com.justbuyit.entity.Company;

/**
 * DAO for managing {@link Company} entities (aka. customers).
 */
public interface CompanyDAO {

    /**
     * Creates a new company (aka. customer).
     * 
     * @param company
     *            the company
     * @return the persisted company
     */
    public abstract Company create(Company company);

    /**
     * Updates the given company.
     * 
     * @param company
     *            the company
     */
    public abstract Company update(Company company);

    /**
     * Deletes the given company.
     * 
     * @param companyId
     *            the company ID
     */
    public abstract void delete(Company company);

    /**
     * Finds a company with the given ID.
     * 
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
