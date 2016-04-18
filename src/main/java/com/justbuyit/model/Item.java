package com.justbuyit.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Item {

    private Integer quantity;
    private String unit;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Item [quantity=").append(quantity).append(", unit=").append(unit).append("]");
        return builder.toString();
    }

}
