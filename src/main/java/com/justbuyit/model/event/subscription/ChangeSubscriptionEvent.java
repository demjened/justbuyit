package com.justbuyit.model.event.subscription;

import javax.xml.bind.annotation.XmlRootElement;

import com.justbuyit.model.Account;
import com.justbuyit.model.Order;
import com.justbuyit.model.event.Event;
import com.justbuyit.model.event.EventType;
import com.justbuyit.model.event.Payload;
import com.justbuyit.model.event.subscription.ChangeSubscriptionEvent.ChangeSubscriptionPayload;

@XmlRootElement(name = "event")
public class ChangeSubscriptionEvent extends Event<ChangeSubscriptionPayload> {
    
    @XmlRootElement(name = "payload")
    public static class ChangeSubscriptionPayload extends Payload {

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

    }
    
    public ChangeSubscriptionEvent() {
        super(EventType.SUBSCRIPTION_CHANGE);
    }
    
    @Override
    public void setPayload(ChangeSubscriptionPayload payload) {
        super.setPayload(payload);
    }
    
    @Override
    public ChangeSubscriptionPayload getPayload() {
        return super.getPayload();
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getSimpleName()).append(": ").append(getPayload().getAccount().getAccountIdentifier()).append(" -> ")
                .append(getPayload().getOrder().getEditionCode());
        return builder.toString();
    }

}
