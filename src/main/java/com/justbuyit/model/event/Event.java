package com.justbuyit.model.event;

import javax.xml.bind.annotation.XmlTransient;

import com.justbuyit.model.Marketplace;
import com.justbuyit.model.User;

public abstract class Event<P extends Payload> {

    private final EventType type;
    private Marketplace marketplace;
    private String flag;
    private User creator;
    
    @XmlTransient
    private P payload;

    public Event(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
    
    @XmlTransient
    public P getPayload() {
        return payload;
    }

    public void setPayload(P payload) {
        this.payload = payload;
    }

}
