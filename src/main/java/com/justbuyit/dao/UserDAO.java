package com.justbuyit.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.User;

@Repository
public interface UserDAO {

    public abstract void assign(User user, String id) throws JustBuyItException;
    
    public abstract void unassign(User user, String id) throws JustBuyItException;
 
    public abstract List<User> findAll();
    
}
