package com.justbuyit.dao;

import java.util.List;

import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.Company;

public interface CompanyDAO {

    public abstract void add(Company company) throws JustBuyItException;
    
    public abstract void delete(String id) throws JustBuyItException;
    
    public abstract List<Company> findAll();

}
