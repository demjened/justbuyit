package com.justbuyit.model;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {

    private String editionCode;
    private String pricingDuration;
    private Collection<Item> items;

    public String getEditionCode() {
        return editionCode;
    }

    public void setEditionCode(String editionCode) {
        this.editionCode = editionCode;
    }

    public String getPricingDuration() {
        return pricingDuration;
    }

    public void setPricingDuration(String pricingDuration) {
        this.pricingDuration = pricingDuration;
    }

    @XmlElement(name = "item")
    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((editionCode == null) ? 0 : editionCode.hashCode());
        result = prime * result + ((pricingDuration == null) ? 0 : pricingDuration.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (editionCode == null) {
            if (other.editionCode != null)
                return false;
        } else if (!editionCode.equals(other.editionCode))
            return false;
        if (pricingDuration == null) {
            if (other.pricingDuration != null)
                return false;
        } else if (!pricingDuration.equals(other.pricingDuration))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Order [editionCode=").append(editionCode).append(", pricingDuration=").append(pricingDuration).append(", items=").append(items)
                .append("]");
        return builder.toString();
    }

}
