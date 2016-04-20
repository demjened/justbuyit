package com.justbuyit.dao;

import java.util.Set;

import com.google.inject.internal.Sets;

public class InMemoryProfileDAO implements ProfileDAO {

    private final Set<String> openIds = Sets.newHashSet();
    
    @Override
    public void addOpenId(String openId) {
        openIds.add(openId);
    }

    @Override
    public boolean containsOpenId(String openId) {
        return openIds.contains(openId);
    }

    @Override
    public void removeOpenId(String openId) {
        openIds.remove(openId);
    }

}
