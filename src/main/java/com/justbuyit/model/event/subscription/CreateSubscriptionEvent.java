package com.justbuyit.model.event.subscription;

import javax.xml.bind.annotation.XmlRootElement;

import com.justbuyit.model.Company;
import com.justbuyit.model.Order;
import com.justbuyit.model.event.Event;
import com.justbuyit.model.event.EventType;
import com.justbuyit.model.event.Payload;
import com.justbuyit.model.event.subscription.CreateSubscriptionEvent.CreateSubscriptionPayload;

@XmlRootElement(name = "event")
public class CreateSubscriptionEvent extends Event<CreateSubscriptionPayload> {

    @XmlRootElement(name = "payload")
    public static class CreateSubscriptionPayload extends Payload {

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
        
    }
    
    public CreateSubscriptionEvent() {
        super(EventType.SUBSCRIPTION_ORDER);
    }
    
    @Override
    public void setPayload(CreateSubscriptionPayload payload) {
        super.setPayload(payload);
    }
    
    @Override
    public CreateSubscriptionPayload getPayload() {
        return super.getPayload();
    }

}
