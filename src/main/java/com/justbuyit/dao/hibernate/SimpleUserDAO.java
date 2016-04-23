package com.justbuyit.dao.hibernate;

import javax.transaction.Transactional;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.justbuyit.dao.UserDAO;
import com.justbuyit.entity.User;

/**
 * Hibernate powered {@link UserDAO}.
 */
@Repository
@Transactional
public class SimpleUserDAO extends BaseDAO implements UserDAO {

    @Override
    public User findByOpenId(String openId) {
        return (User) getSession().createCriteria(User.class)
                                  .add(Restrictions.eq("openId", openId))
                                  .uniqueResult();
    }

    @Override
    public User update(User user) {
        return (User) getSession().merge(user);
    }

}
