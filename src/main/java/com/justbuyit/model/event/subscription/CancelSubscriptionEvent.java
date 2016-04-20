package com.justbuyit.model.event.subscription;

import javax.xml.bind.annotation.XmlRootElement;

import com.justbuyit.model.Account;
import com.justbuyit.model.event.Event;
import com.justbuyit.model.event.EventType;
import com.justbuyit.model.event.Payload;
import com.justbuyit.model.event.subscription.CancelSubscriptionEvent.CancelSubscriptionPayload;

@XmlRootElement(name = "event")
public class CancelSubscriptionEvent extends Event<CancelSubscriptionPayload> {

    @XmlRootElement(name = "payload")
    public static class CancelSubscriptionPayload extends Payload {

        private Account account;

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }
        
    }
    
    public CancelSubscriptionEvent() {
        super(EventType.SUBSCRIPTION_CANCEL);
    }
    
    @Override
    public void setPayload(CancelSubscriptionPayload payload) {
        super.setPayload(payload);
    }
    
    @Override
    public CancelSubscriptionPayload getPayload() {
        return super.getPayload();
    }

}
