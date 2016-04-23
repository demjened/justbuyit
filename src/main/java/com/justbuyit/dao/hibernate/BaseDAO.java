package com.justbuyit.dao.hibernate;

import org.hibernate.Session;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public class BaseDAO extends HibernateDaoSupport {

    protected Session getSession() {
        return getSessionFactory().getCurrentSession();
    }
    
}
