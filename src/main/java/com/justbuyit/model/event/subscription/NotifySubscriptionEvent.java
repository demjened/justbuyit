package com.justbuyit.model.event.subscription;

import javax.xml.bind.annotation.XmlRootElement;

import com.justbuyit.model.Account;
import com.justbuyit.model.Notice;
import com.justbuyit.model.event.Event;
import com.justbuyit.model.event.EventType;
import com.justbuyit.model.event.Payload;
import com.justbuyit.model.event.subscription.NotifySubscriptionEvent.NotifySubscriptionPayload;

@XmlRootElement(name = "event")
public class NotifySubscriptionEvent extends Event<NotifySubscriptionPayload> {

    @XmlRootElement(name = "payload")
    public static class NotifySubscriptionPayload extends Payload {

        private Account account;
        private Notice notice;

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }

        public Notice getNotice() {
            return notice;
        }

        public void setNotice(Notice notice) {
            this.notice = notice;
        }
        
    }
    
    public NotifySubscriptionEvent() {
        super(EventType.SUBSCRIPTION_NOTICE);
    }
    
    @Override
    public void setPayload(NotifySubscriptionPayload payload) {
        super.setPayload(payload);
    }
    
    @Override
    public NotifySubscriptionPayload getPayload() {
        return super.getPayload();
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getSimpleName()).append(": ").append(getPayload().getAccount().getAccountIdentifier()).append(" -> ")
                .append(getPayload().getNotice().getType());
        return builder.toString();
    }


}
