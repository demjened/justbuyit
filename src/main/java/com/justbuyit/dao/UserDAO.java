package com.justbuyit.dao;

import com.justbuyit.exception.JustBuyItException;
import com.justbuyit.model.User;

public interface UserDAO {

    /**
     * Assigns the given user to the given company.
     * 
     * @param user
     *            the user
     * @param companyId
     *            the company ID
     * @throws JustBuyItException
     *             if there was an error during the action
     */
    public abstract void assign(User user, String companyId) throws JustBuyItException;

    /**
     * Unassigns the given user from the given company.
     * 
     * @param user
     *            the user
     * @param companyId
     *            the company ID
     * @throws JustBuyItException
     *             if there was an error during the action
     */
    public abstract void unassign(User user, String companyId) throws JustBuyItException;

    /**
     * Removes all users from the given company.
     * 
     * @param companyId
     *            the company ID
     * @throws JustBuyItException
     *             if there was an error during the action
     */
    public abstract void removeAll(String companyId) throws JustBuyItException;

    /**
     * Checks whether the given user is already authenticated.
     * 
     * @param openId
     *            the OpenID of the user
     * @return true if the user is authenticated
     */
    public abstract boolean isAuthenticated(String openId);

    /**
     * Sets the authentication status of the given user
     * 
     * @param openId
     *            the OpenID of the user
     * @param authenticated
     *            whether the user is authenticated
     * @throws JustBuyItException
     *             if there was an error during the action
     */
    public abstract void setAuthenticated(String openId, boolean authenticated) throws JustBuyItException;

}
