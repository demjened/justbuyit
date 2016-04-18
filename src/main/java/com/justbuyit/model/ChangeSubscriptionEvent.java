package com.justbuyit.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "event")
public class ChangeSubscriptionEvent extends Event {
    
    private ChangeSubscriptionPayload payload;

    public ChangeSubscriptionEvent() {
        super(Type.SUBSCRIPTION_CHANGE);
    }

    public ChangeSubscriptionPayload getPayload() {
        return payload;
    }

    public void setPayload(ChangeSubscriptionPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ChangeSubscriptionEvent [payload=").append(payload).append(", toString()=").append(super.toString()).append("]");
        return builder.toString();
    }

}
