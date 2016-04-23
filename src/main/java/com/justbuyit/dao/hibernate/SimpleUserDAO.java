package com.justbuyit.dao.hibernate;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.justbuyit.dao.UserDAO;
import com.justbuyit.entity.User;
import com.justbuyit.exception.JustBuyItException;

@Repository
@Transactional
public class SimpleUserDAO extends BaseDAO implements UserDAO {

    @Override
    public User findByOpenId(String openId) {
        return (User) getSession().get(User.class, openId);
    }

    @Override
    public User update(User user) throws JustBuyItException {
        return (User) getSession().merge(user);
    }

}
