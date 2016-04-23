package com.justbuyit.dao.hibernate;

import org.hibernate.Session;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * Base DAO class for convenienty getting the Hibernate session.
 */
public class BaseDAO extends HibernateDaoSupport {

    /**
     * Gets the Hibernate session.
     * 
     * @return the session
     */
    protected Session getSession() {
        return getSessionFactory().getCurrentSession();
    }

}
