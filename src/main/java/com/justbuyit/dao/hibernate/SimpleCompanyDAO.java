package com.justbuyit.dao.hibernate;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.justbuyit.dao.CompanyDAO;
import com.justbuyit.entity.Company;

/**
 * Hibernate powered {@link CompanyDAO}.
 */
@Repository
@Transactional
@SuppressWarnings("unchecked")
public class SimpleCompanyDAO extends BaseDAO implements CompanyDAO {

    @Override
    public Company create(Company company) {
        getSession().save(company);
        return company;
    }

    @Override
    public Company update(Company company) {
        return (Company) getSession().merge(company);
    }

    @Override
    public void delete(Company company) {
        getSession().delete(company);
    }

    @Override
    public Company findById(String companyId) {
        return (Company) getSession().get(Company.class, companyId);
    }

    @Override
    public List<Company> findAll() {
        return getSession().createCriteria(Company.class).list();
    }

}
