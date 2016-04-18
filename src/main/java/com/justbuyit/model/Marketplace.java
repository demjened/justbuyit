package com.justbuyit.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Marketplace {

    private String baseUrl;
    private String partner;
    
    public String getBaseUrl() {
        return baseUrl;
    }
    
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    public String getPartner() {
        return partner;
    }
    
    public void setPartner(String partner) {
        this.partner = partner;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Marketplace [baseUrl=").append(baseUrl).append(", partner=").append(partner).append("]");
        return builder.toString();
    }
    
}
