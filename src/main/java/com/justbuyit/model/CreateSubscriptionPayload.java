package com.justbuyit.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "payload")
public class CreateSubscriptionPayload {

    private Company company;
    private Order order;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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
        builder.append("CreateSubscriptionPayload [company=").append(company).append(", order=").append(order).append("]");
        return builder.toString();
    }
    
}
