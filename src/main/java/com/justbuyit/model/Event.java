package com.justbuyit.model;

public abstract class Event {

    public static enum Type {
        SUBSCRIPTION_ORDER,
        SUBSCRIPTION_CHANGE;
    }

    private final Type typeValue;
    private String type;
    private Marketplace marketplace;
    private String flag;
    private User creator;

    public Event(Type type) {
        this.typeValue = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Event [type=").append(type).append("/").append(typeValue).append(", marketplace=").append(marketplace).append(", creator=").append(creator).append("]");
        return builder.toString();
    }

}
