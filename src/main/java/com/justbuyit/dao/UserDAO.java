package com.justbuyit.dao;

import com.justbuyit.entity.User;

/**
 * DAO for managing {@link User} entities.
 */
public interface UserDAO {

    /**
     * Finds a user by the given OpenID.
     * 
     * @param openId
     *            the OpenID identifier
     * @return the user if found, null otherwise
     */
    public abstract User findByOpenId(String openId);

    /**
     * Updates the given user.
     * 
     * @param user
     *            the user
     */
    public abstract User update(User user);

}
