package com.justbuyit.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Notice {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
