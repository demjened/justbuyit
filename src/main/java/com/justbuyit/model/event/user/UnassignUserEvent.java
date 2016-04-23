package com.justbuyit.model.event.user;

import javax.xml.bind.annotation.XmlRootElement;

import com.justbuyit.model.Account;
import com.justbuyit.model.User;
import com.justbuyit.model.event.Event;
import com.justbuyit.model.event.EventType;
import com.justbuyit.model.event.Payload;
import com.justbuyit.model.event.user.UnassignUserEvent.UnassignUserPayload;

@XmlRootElement(name = "event")
public class UnassignUserEvent extends Event<UnassignUserPayload> {

    @XmlRootElement(name = "payload")
    public static class UnassignUserPayload extends Payload {

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

    public UnassignUserEvent() {
        super(EventType.USER_UNASSIGNMENT);
    }

    @Override
    public void setPayload(UnassignUserPayload payload) {
        super.setPayload(payload);
    }

    @Override
    public UnassignUserPayload getPayload() {
        return super.getPayload();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getSimpleName()).append(": ").append(getPayload().getAccount().getAccountIdentifier()).append(" -> ")
                .append(getPayload().getUser().getUuid());
        return builder.toString();
    }

}
