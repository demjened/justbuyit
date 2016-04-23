package com.justbuyit.dao;

import com.justbuyit.entity.User;
import com.justbuyit.exception.JustBuyItException;

public interface UserDAO {

    public abstract User findByOpenId(String openId);
    
    public abstract User update(User user) throws JustBuyItException;
    
}
