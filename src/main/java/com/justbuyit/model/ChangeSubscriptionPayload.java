package com.justbuyit.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "payload")
public class ChangeSubscriptionPayload {

    private Account account;
    private Order order;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ChangeSubscriptionPayload [account=").append(account).append(", order=").append(order).append("]");
        return builder.toString();
    }
    
}
