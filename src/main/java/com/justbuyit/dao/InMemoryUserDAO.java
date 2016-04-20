package com.justbuyit.dao;

import java.util.List;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.internal.Lists;
import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.User;

public class InMemoryUserDAO implements UserDAO {

    private final MultiValuedMap<String, User> userAssignments = new ArrayListValuedHashMap<String, User>();
    
    private final static Logger LOG = LoggerFactory.getLogger(InMemoryUserDAO.class);
    
    @Override
    public void assign(User user, String id) throws JustBuyItException {
        
        LOG.info("put " + id + " - " + user);
        userAssignments.put(id, user);
    }

    @Override
    public void unassign(User user, String id) throws JustBuyItException {
        userAssignments.removeMapping(id, user);
    }

    @Override
    public List<User> findAll() {
        return Lists.newArrayList(userAssignments.values());
    }
    
}
