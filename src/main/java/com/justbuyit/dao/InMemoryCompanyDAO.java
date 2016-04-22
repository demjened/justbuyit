package com.justbuyit.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.google.inject.internal.Lists;
import com.google.inject.internal.Maps;
import com.justbuyit.exception.AccountNotFoundException;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.exception.UserAlreadyExistsException;
import com.justbuyit.model.Company;

public class InMemoryCompanyDAO implements CompanyDAO {

    private final static Logger LOG = LoggerFactory.getLogger(InMemoryCompanyDAO.class);
    
    private final Map<String, Company> companies = Maps.newHashMap();
    
    @Override
    public String add(Company company) throws JustBuyItException {
        Assert.notNull(company);
        Assert.notNull(company.getUuid());
        
        String companyId = company.getUuid();
        LOG.info("Adding company with ID [{}]", companyId);
        
        // check if the company already exists
        if (companies.containsKey(companyId)) {
            throw new UserAlreadyExistsException(String.format("Company [%s] already exists with ID [%s]", company.getName(), companyId));
        }
        
        companies.put(companyId, company);
        return companyId;
    }
    
    @Override
    public void delete(String companyId) throws JustBuyItException {
        LOG.info("Deleting company with ID [{}]", companyId);

        // check if the company exists
        checkCompanyExists(companyId);

        companies.remove(companyId);
    }
    
    @Override
    public List<Company> findAll() {
        LOG.info("Fetching all companies");
        
        return Lists.newArrayList(companies.values());
    }
    
    @Override
    public void updateSubscriptionStatus(String companyId, String subscriptionStatus) throws JustBuyItException {
        LOG.info("Updating subscription status for company [{}] to [{}]", companyId, subscriptionStatus);
        
        // check if the company exists
        checkCompanyExists(companyId);
        
        companies.get(companyId).setSubscriptionStatus(subscriptionStatus);
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
        if (!companies.containsKey(companyId)) {
            throw new AccountNotFoundException(String.format("Company [%s] does not exist", companyId));
        }
    }
    
}
