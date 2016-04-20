package com.justbuyit.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.google.inject.internal.Lists;
import com.google.inject.internal.Maps;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.exception.UserAlreadyExistsException;
import com.justbuyit.exception.UserNotFoundException;
import com.justbuyit.model.Company;

@Repository
public class InMemoryCompanyDAO implements CompanyDAO {

    private final static Logger LOG = LoggerFactory.getLogger(InMemoryCompanyDAO.class);
    
    private final Map<String, Company> companies = Maps.newHashMap();
    
    @Override
    public void add(Company company) throws JustBuyItException {
        Assert.notNull(company);
        
        String id = company.getUuid();
        if (companies.containsKey(id)) {
            throw new UserAlreadyExistsException(String.format("Company [%s] already exists with ID [%s]", company.getName(), id));
        }
        
        LOG.info("Adding company with ID [{}]", id);
        companies.put(id, company);
    }
    
    @Override
    public void delete(String id) throws JustBuyItException {
        if (!companies.containsKey(id)) {
            throw new UserNotFoundException(String.format("Company [%s] does not exist", id));
        }

        LOG.info("Deleting company with ID [{}]", id);
        companies.remove(id);
    }
    
    @Override
    public List<Company> findAll() {
        LOG.info("Fetching all companies");
        
        return Lists.newArrayList(companies.values());
    }
    
}
