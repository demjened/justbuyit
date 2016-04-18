package com.justbuyit.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "event")
public class CreateSubscriptionEvent extends Event {

    public CreateSubscriptionEvent() {
        super(Type.SUBSCRIPTION_ORDER);
    }

    private CreateSubscriptionPayload payload;

    public CreateSubscriptionPayload getPayload() {
        return payload;
    }

    public void setPayload(CreateSubscriptionPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CreateSubscriptionEvent [payload=").append(payload).append(", toString()=").append(super.toString()).append("]");
        return builder.toString();
    }
    
}
