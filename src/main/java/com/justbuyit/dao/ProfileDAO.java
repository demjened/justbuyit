package com.justbuyit.dao;

public interface ProfileDAO {

    public abstract void addOpenId(String openId);

    public abstract boolean containsOpenId(String openId);
    
    public abstract void removeOpenId(String openId);
    
}
