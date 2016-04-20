package com.justbuyit.model.event.user;

import javax.xml.bind.annotation.XmlRootElement;

import com.justbuyit.model.Account;
import com.justbuyit.model.User;
import com.justbuyit.model.event.Event;
import com.justbuyit.model.event.EventType;
import com.justbuyit.model.event.Payload;
import com.justbuyit.model.event.user.AssignUserEvent.AssignUserPayload;

@XmlRootElement(name = "event")
public class AssignUserEvent extends Event<AssignUserPayload> {

    @XmlRootElement(name = "payload")
    public static class AssignUserPayload extends Payload {

        private Account account;
        private User user;

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
        
    }
    
    public AssignUserEvent() {
        super(EventType.USER_ASSIGNMENT);
    }
    
    @Override
    public void setPayload(AssignUserPayload payload) {
        super.setPayload(payload);
    }
    
    @Override
    public AssignUserPayload getPayload() {
        return super.getPayload();
    }

}
