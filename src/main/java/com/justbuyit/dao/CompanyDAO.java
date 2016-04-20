package com.justbuyit.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.Company;

@Repository
public interface CompanyDAO {

    public abstract void add(Company company) throws JustBuyItException;
    
    public abstract void delete(String id) throws JustBuyItException;
    
    public abstract List<Company> findAll();

}
